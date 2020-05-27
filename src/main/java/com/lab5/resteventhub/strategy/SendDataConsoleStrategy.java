package com.lab5.resteventhub.strategy;

import com.lab5.resteventhub.service.impl.SendDataConsoleBehaviourImpl;

public class SendDataConsoleStrategy extends AbstractStrategy {

    public SendDataConsoleStrategy() {
        sendBehaviour = new SendDataConsoleBehaviourImpl();
    }
}