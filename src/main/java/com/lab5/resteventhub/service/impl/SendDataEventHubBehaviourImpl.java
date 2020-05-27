package com.lab5.resteventhub.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab5.resteventhub.service.SendDataBehaviour;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class SendDataEventHubBehaviourImpl implements SendDataBehaviour {

    private static final String NAMESPACE_NAME = "eventhub9";
    private static final String EVENT_HUB_NAME = "lab9";
    private static final String SAS_KEY_NAME = "Endpoint=sb://eventhub9.servicebus.windows.net/;SharedAccessKeyName=lab9;SharedAccessKey=689IfeppjmGynuibrJSg9ciV1k46yk8EAlF4XxSl06A=";
    private static final String SAS_KEY = "689IfeppjmGynuibrJSg9ciV1k46yk8EAlF4XxSl06A=";

    public void sendAndLog(JSONArray jsonArray) {
        final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                .setNamespaceName(NAMESPACE_NAME)//namespace
                .setEventHubName(EVENT_HUB_NAME)//hub name
                /*Connection string–primary key*/
                .setSasKeyName(SAS_KEY_NAME)
                /*Primary key*/
                .setSasKey(SAS_KEY);

        final Gson gson = new GsonBuilder().create();
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

        final EventHubClient ehClient;
        try {
            ehClient = EventHubClient.createSync(connStr.toString(), executorService);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                System.out.println("Document: " + i);
                byte[] payloadBytes = gson.toJson(jsonObject).getBytes(Charset.defaultCharset());
                EventData sendEvent = EventData.create(payloadBytes);

                ehClient.sendSync(sendEvent);
            }
        } catch (EventHubException | IOException e) {
            e.printStackTrace();
        }
    }
}
