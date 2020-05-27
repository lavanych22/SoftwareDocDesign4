package com.lab5.resteventhub.strategy;

import com.lab5.resteventhub.service.SendDataBehaviour;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public abstract class AbstractStrategy {
    SendDataBehaviour sendBehaviour;

    public AbstractStrategy() {
    }

    public void fetchFromAndTransfer(String url) {
        try {
            URL data = new URL(url);
            HttpURLConnection con = (HttpURLConnection) data.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            int count = 1;
            int startRaw = 1;
            int limit = 100;
            int endRaw = 0;
            JSONArray jsonArray;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
                if (count == limit) {
                    jsonArray = new JSONArray(response.toString() + ']');
                    System.out.println(response.toString());
                    System.out.println("LENGTH: " + jsonArray.length());
                    endRaw = endRaw + count;
                    startRaw = startRaw + count;
                    sendBehaviour.sendAndLog(jsonArray);
                    count = 0;
                }
                count += 1;
            }
            br.close();

            System.out.println(Instant.now() + ": Send Complete...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SendDataBehaviour getSendBehaviour() {
        return sendBehaviour;
    }

    public void setSendBehaviour(SendDataBehaviour sendBehaviour) {
        this.sendBehaviour = sendBehaviour;
    }
}
