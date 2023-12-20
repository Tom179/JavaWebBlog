package Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class Json {
    public static JsonObject Parse(HttpServletRequest req) throws IOException {
        StringBuilder jsonRequest = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
        }

        JsonObject jsonObject = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
        return jsonObject;
    }
}
