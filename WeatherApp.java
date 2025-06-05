import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {

    private static final String API_KEY = "c051a268661c4c5193c132220250406"; // Your API key
    private static final String CITY = "Hyderabad"; // Replace with your city
    private static final String URL_STRING = "https://api.weatherapi.com/v1/current.json?key=" 
                                             + API_KEY + "&q=" + CITY;

    public static void main(String[] args) {
        try {
            // Establish connection
            URL url = new URL(URL_STRING);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Convert response to string
            String responseString = response.toString();

            // Extract specific weather details manually
            String temperature = extractValue(responseString, "\"temp_c\":", ",");
            String humidity = extractValue(responseString, "\"humidity\":", ",");
            String windSpeed = extractValue(responseString, "\"wind_kph\":", ",");
            String condition = extractValue(responseString, "\"text\":\"", "\"");

            // Display formatted output
            System.out.println("\n===== Weather Information for " + CITY + " =====");
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Wind Speed: " + windSpeed + " km/h");
            System.out.println("Weather Condition: " + condition);
            System.out.println("=====================================\n");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Helper method to extract values from JSON response manually
    private static String extractValue(String data, String key, String endSymbol) {
        int startIndex = data.indexOf(key) + key.length();
        int endIndex = data.indexOf(endSymbol, startIndex);
        return data.substring(startIndex, endIndex).replace("\"", "").trim();
    }
}
