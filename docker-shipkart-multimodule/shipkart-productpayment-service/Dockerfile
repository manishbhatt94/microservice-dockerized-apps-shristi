# ============================================================
# Stage 1 — Build
# ============================================================

# Build stage (newer OS, full JDK + Maven)
# OS - Ubuntu 24.04 "Noble" LTS
# https://hub.docker.com/layers/library/maven/3.9-eclipse-temurin-25-noble/

FROM maven:3.9-eclipse-temurin-25-noble AS builder

WORKDIR /build

# Copy parent POM first — Maven needs it to resolve inherited config.
# This works because docker-compose.yml sets context: . (project root)
COPY pom.xml ./pom.xml

# Copy ALL six module POMs so Maven can verify the full
# project structure without crashing at startup.
# Only one of these modules will actually be compiled —
# but all folders must exist for Maven to verify the <modules>
# element in the root POM.
COPY shipkart-api-gateway/pom.xml               ./shipkart-api-gateway/pom.xml
COPY shipkart-config-server/pom.xml             ./shipkart-config-server/pom.xml
COPY shipkart-productcart-service/pom.xml       ./shipkart-productcart-service/pom.xml
COPY shipkart-productcatalog-service/pom.xml    ./shipkart-productcatalog-service/pom.xml
COPY shipkart-productinfo-service/pom.xml       ./shipkart-productinfo-service/pom.xml
COPY shipkart-productinventory-service/pom.xml  ./shipkart-productinventory-service/pom.xml
COPY shipkart-productorder-service/pom.xml      ./shipkart-productorder-service/pom.xml
COPY shipkart-productpayment-service/pom.xml    ./shipkart-productpayment-service/pom.xml

# NOW Maven can scan the project without crashing.
# -pl (Project List) targets only this module,
# -am (Also Make) loads the parent, for shared configuration etc,
# --no-transfer-progress - Silences only the download progress bars from Maven,
# -B - (Batch mode) disables color formatting & disables interactive prompts where
# Maven asks for user confirmation via keyboard keypress.

# --mount=type=cache tells BuildKit to mount a persistent cache folder
# at /root/.m2 for the duration of this RUN command only.
# This is for Maven dependency caching so that every build doesn't download the same
# dependencies from the internet.
# This folder lives on your host machine, is managed by Docker's BuildKit and survives
# container destruction.
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline \
    -pl shipkart-productpayment-service \
    -am \
    --no-transfer-progress \
    -B

# Now copy the actual source
COPY shipkart-productpayment-service/src ./shipkart-productpayment-service/src

# Build only this module (-pl), no need to build siblings (-am still
# needed so Maven loads the parent, but siblings have no src to compile)
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package \
    -pl shipkart-productpayment-service \
    -am \
    -DskipTests \
    --no-transfer-progress \
    -B


# ============================================================
# Stage 2 — Run (JRE only, not full JDK)
# ============================================================

# Runtime stage (stable OS, lean JRE)
# OS - Ubuntu 22.04 "Jammy" LTS (More stable & mature than "Noble")
# https://hub.docker.com/layers/library/eclipse-temurin/25-jre-jammy/

FROM eclipse-temurin:25-jre-jammy AS runner

# Install curl for the healthcheck command
RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN addgroup --system appgroup \
    && adduser --system --ingroup appgroup appuser
USER appuser

COPY --from=builder \
    /build/shipkart-productpayment-service/target/*.jar \
    product-payment.jar

EXPOSE 8086

ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", "product-payment.jar"]
