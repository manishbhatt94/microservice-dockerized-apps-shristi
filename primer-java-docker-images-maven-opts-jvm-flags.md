# Understanding Java Docker Base Images, Maven Options & JVM Flags


Really good questions. Let me go through everything from the ground up.

---

## Part 1 — The Base Images Explained

### `FROM maven:3.9-eclipse-temurin-21`

Let's break the image name into parts: `maven` is the image name, `3.9` is the Maven version, `eclipse-temurin-21` is the JDK flavour and version baked into it.

**The OS underneath:** This image is based on **Ubuntu 22.04 LTS (Jammy)**. It is not a full desktop Ubuntu — it is a minimal server variant. Think of it like Ubuntu with everything graphical, audio, printer drivers, and other desktop nonsense stripped out. Only the core OS libraries, package manager, shell, and filesystem remain. It is enough to run a JVM and Maven, nothing more.

**glibc:** Ubuntu uses the GNU C Library (glibc) as its core system library. Almost all Java distributions are compiled against glibc. This is why it just works, with no compatibility issues.

**What JDK is in it:** Eclipse Temurin 21. Temurin is the free, open-source JDK distribution maintained by the Adoptium project (backed by major companies like IBM, Microsoft, Red Hat). It is not Oracle's JDK — it is a fully compatible, freely licensed alternative. Version 21 is a Long Term Support (LTS) release, meaning it receives security patches for years, unlike short-lived versions like 22, 23, 24.

**Does it have JDK or JRE:** Full JDK. This is intentional for the builder stage — you need the compiler (`javac`), Maven, and all build tools here. Image size does not matter for the builder stage because it is thrown away after the JAR is created. Only the final runtime image size matters.

---

### `FROM eclipse-temurin:21-jre-jammy`

Breaking this down: `eclipse-temurin` is the image name, `21` is the Java version, `jre` means only the runtime (not the full JDK), `jammy` is the Ubuntu 22.04 LTS codename.

**The OS underneath:** Same Ubuntu 22.04 minimal base as above. Same glibc. Same compatibility guarantees.

**JRE vs JDK here:** This image contains only the **JRE — Java Runtime Environment**. No compiler, no `javac`, no build tools. Just enough Java to run an already-compiled program. This is exactly what you need for the runtime stage — your JAR is already built, you just need to run it.

**Is Jammy (Ubuntu 22.04) too old from a security perspective?** This is a very thoughtful concern, and the answer is **no, not at all** — here is why. Ubuntu LTS releases receive **5 years of standard security patches** (until April 2027 for Jammy) and up to 10 years under extended support. Canonical (the company behind Ubuntu) actively backports security fixes to 22.04 even when newer Ubuntu versions exist. The Eclipse Temurin team also regularly publishes updated Docker images that include the latest OS-level security patches. As long as you pull a fresh image (not a year-old cached one), `jammy` is completely secure for production use. The codename just tells you which Ubuntu release the base is — it says nothing about whether it is patched or not.

**Why not use Ubuntu 24.04 (Noble)?** You could — `eclipse-temurin:21-jre-noble` exists. But `jammy` is more battle-tested, has wider community verification, and is the default the Temurin team has most thoroughly tested. For your learning phase it makes zero difference.

---

## Part 2 — What is a JAR and Why Maven Exists

Before explaining the Maven commands, you need to understand the problem Maven is solving.

### You already know this:
```
javac HelloWorld.java   →   produces HelloWorld.class
java HelloWorld         →   runs HelloWorld.class
```

### The problem with real apps

A real Spring Boot app is not one file. Your `shipkart-productcart-service` probably has dozens of your own `.java` files across many folders. But more importantly, it uses **libraries written by other people** — Spring Boot itself, Hibernate for database access, Jackson for JSON, and many more. These are not your code. You did not write them. But your code calls their code.

Each of those libraries is itself a collection of hundreds of compiled `.class` files. Downloading, managing, and compiling against all of them manually would be a nightmare.

### What a JAR is

JAR stands for **Java ARchive**. It is literally just a ZIP file with a `.jar` extension containing compiled `.class` files. When you use Spring Boot, you are using Spring's JAR files. When your app is built, Maven packages everything — your compiled classes plus all the library JARs — into one single fat JAR (also called an uber JAR). That one file is self-contained and runnable. This is what `java -jar product-cart.jar` runs.

### What Maven is

Maven is a build tool — its job is to:

1. Read your `pom.xml` to understand what your project is and what libraries it needs
2. Download those libraries from the internet (from Maven Central, a huge public repository of Java libraries — think npm registry but for Java)
3. Compile all your `.java` source files in the correct order, with all those libraries available
4. Run your tests
5. Package everything into a single runnable JAR file

The `pom.xml` is to Maven what `package.json` is to npm. The `~/.m2` folder on your machine is Maven's local cache of downloaded libraries — equivalent to `node_modules` but shared across all your Java projects.

---

## Part 3 — The Maven Commands Explained

### Command 1: `mvn dependency:go-offline`

```dockerfile
RUN mvn dependency:go-offline \
    -pl shipkart-productcart-service \
    -am \
    --no-transfer-progress \
    -B
```

**What `dependency:go-offline` does:**

This tells Maven: "Read the `pom.xml`, figure out every single library this project needs, and download all of them into the local Maven cache (`/root/.m2`) right now. After this, I should be able to build without any internet access."

The reason this is a separate step before the actual build is purely about **Docker layer caching** — the same reason you do this in Node.js Dockerfiles:

```dockerfile
# Node.js equivalent you probably recognise:
COPY package.json .
RUN npm install        # ← download dependencies first, separate layer
COPY src ./src         # ← then copy source code
RUN npm run build
```

In Docker, each `RUN`, `COPY`, `ADD` instruction creates a layer. Docker caches these layers. If nothing changed in a layer, Docker reuses the cached version and skips re-running it. By downloading dependencies before copying your source code:

- If you only change your Java source code → Docker reuses the cached dependency layer → rebuild is fast
- If you change `pom.xml` (add a new library) → Docker re-downloads dependencies → then recompiles

Without this separation, every single code change would re-download hundreds of MB of libraries from the internet. Slow and wasteful.

**The flags:**

`-pl shipkart-productcart-service` — `-pl` stands for **Project List**. This is a multi-module project with 6 modules. Without this flag, Maven would try to build all 6 modules. This flag says "only work with this specific module." Think of it as targeting one specific package in a monorepo.

`-am` — stands for **Also Make**. This tells Maven "and also process any modules that the targeted module depends on." In your case, `shipkart-productcart-service` inherits configuration from the parent `pom.xml`. The `-am` flag ensures Maven loads and processes the parent module too, even though you are only building the child. Without it, Maven would not find the inherited Java version, Spring Boot version, and other parent-level configuration.

`--no-transfer-progress` — Maven normally prints a progress bar for every file it downloads. Inside a Docker build, this creates thousands of lines of noisy, unreadable log output. This flag silences those download progress lines while still showing what is being downloaded.

`-B` — stands for **Batch mode**. By default, Maven sometimes prompts for user input or formats output assuming a human is watching a terminal with color support. Batch mode disables all interactive prompts and color formatting, producing clean, machine-readable output suitable for automated builds.

---

### Command 2: `mvn clean package`

```dockerfile
RUN mvn clean package \
    -pl shipkart-productcart-service \
    -am \
    -DskipTests \
    --no-transfer-progress \
    -B
```

`-pl`, `-am`, `--no-transfer-progress`, `-B` — same meaning as above.

**What `clean` does:**

Deletes the `target/` directory — the folder where Maven puts all compiled output. This ensures you are building from a completely fresh state with no leftover compiled files from a previous build that might interfere. It is the equivalent of deleting a `dist/` or `build/` folder before a fresh frontend build.

**What `package` does:**

This is a Maven **lifecycle phase**. Maven has a sequence of phases it runs in order:

```
validate → compile → test → package → verify → install → deploy
```

When you say `package`, Maven runs every phase up to and including `package`:

- `compile` — runs `javac` on all your `.java` files, producing `.class` files in `target/classes/`
- `test` — compiles and runs your test files (we skip this, see below)
- `package` — takes all the compiled `.class` files plus all dependency JARs and bundles them into one fat JAR in `target/`

The result is `target/shipkart-productcart-service-0.0.1-SNAPSHOT.jar` — the file the `COPY` instruction in Stage 2 picks up.

**`-DskipTests`:**

The `-D` prefix passes a **property** (a key=value setting) to Maven. `skipTests=true` tells Maven to skip the test phase entirely. In a Docker build, you typically skip tests because: tests are run earlier in the CI pipeline as a separate step, running tests inside Docker build slows things down significantly, and tests often need external services like a running database that are not available during image build time.

---

## Part 4 — The JVM Flags in ENTRYPOINT Explained

```dockerfile
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", "product-cart.jar"]
```

First, understanding the flag prefix conventions:

- `-XX:+FlagName` — enable a JVM feature (the `+` means on)
- `-XX:-FlagName` — disable a JVM feature (the `-` means off)  
- `-XX:FlagName=value` — set a JVM tuning parameter to a specific value
- `-D` — set a Java system property (key=value for your application code to read)

### `-XX:+UseContainerSupport`

**The problem this solves:** The JVM was designed long before containers existed. By default, when the JVM starts, it asks the operating system "how much RAM does this machine have?" and then sizes itself accordingly — setting heap sizes, garbage collector thread counts, etc. based on that answer.

Inside a Docker container, the JVM asks the same question. But without this flag, the JVM sees the **host machine's total RAM** — say 16 GB on your laptop — not the container's memory limit (say 512 MB). The JVM then tries to use several gigabytes of memory, immediately exceeding the container's limit, causing the container to be killed by the OS (an OOM kill — Out Of Memory kill). Your app crashes at startup for seemingly no reason.

`UseContainerSupport` tells the JVM: "You are inside a container. Read the container's memory limits from cgroup files (the Linux container isolation mechanism), not the host machine's total RAM." This flag has been available since Java 8u191 and is actually enabled by default from Java 11 onwards — but it is good practice to be explicit about it.

### `-XX:MaxRAMPercentage=75.0`

Even with `UseContainerSupport` enabled, the JVM needs to know how much of the container's available memory it is allowed to use for its heap (the memory space where your application's objects live).

This flag says: "Use at most 75% of the container's memory limit for the Java heap." The remaining 25% is reserved for: the JVM itself (non-heap memory — class metadata, JIT compiled code cache, thread stacks), the OS inside the container, and other processes.

For example, if your container has a 512 MB limit: 75% = 384 MB for Java heap, 128 MB left for JVM overhead and OS. Without this flag, the JVM uses a built-in default percentage that may be too conservative (leaving most memory unused) or too aggressive (leaving nothing for JVM overhead, causing crashes).

### `-jar product-cart.jar`

This is not a `-XX` flag — it is a standard Java option. `-jar` tells the `java` command: "The thing I want to run is packaged as a JAR file. Find the `Main-Class` entry in the JAR's manifest file and run that as the entry point." The fat JAR that Spring Boot creates already has this manifest entry pointing to Spring's bootstrap class, which then starts your entire application.

This is the containerized equivalent of clicking the Run button in STS.
