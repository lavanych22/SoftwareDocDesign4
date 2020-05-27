package com.lab5.resteventhub.strategy;

import com.lab5.resteventhub.service.impl.SendDataEventHubBehaviourImpl;

public class SendDataEventHubStrategy extends AbstractStrategy {

    public SendDataEventHubStrategy() {
        sendBehaviour = new SendDataEventHubBehaviourImpl();
    }
}