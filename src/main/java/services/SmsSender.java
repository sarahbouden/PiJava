package services;


import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SmsSender {
    public static boolean  SendSms(String num,String text){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

// Define variables for the message details
        String to = "216"+num;
        String from = "Tuniwonders";

// Construct the JSON object
        JSONObject requestBody = new JSONObject();
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        JSONArray destinations = new JSONArray();
        JSONObject destination = new JSONObject();
        destination.put("to", to);
        destinations.put(destination);
        message.put("destinations", destinations);
        message.put("from", from);
        message.put("text", text);
        messages.put(message);
        requestBody.put("messages", messages);

// Create the RequestBody using the constructed JSON object
        RequestBody body = RequestBody.create(mediaType, requestBody.toString());

// Build the request
        Request request = new Request.Builder()
                .url("https://dkr8wr.api.infobip.com/sms/2/text/advanced")
                .method("POST", body)
                .addHeader("Authorization", "App 204567d9f84b5eae6ec02eb86489f3ee-2934aad2-43fd-4194-bd60-791c9780ac24")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response+"Etat : "+response.isSuccessful());
            return  response.isSuccessful();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
