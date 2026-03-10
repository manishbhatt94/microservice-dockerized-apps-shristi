import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTest {
    public static void main(String[] args) {
        String url = "http://product-info:8082/actuator/health"; // Change this URL as needed
        // System.out.println(java.util.Arrays.toString(args));
        if (args.length == 0) {
            testConnection(url);
        } else {
            testConnection(args[0]);
        }
    }

    private static void testConnection(String url) {
        try {
            System.out.println("\nAttempting HTTP GET connection to: " + url);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Connection successful (HTTP Response Status Code IS 200): " + url);
            } else {
                System.out.println("Connection failed (HTTP Response Status Code IS NOT 200): " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error connecting to the service [" + url + "]: " + e.getMessage());
        }
    }
}
