package com.lab5.resteventhub.web.rest;

import com.lab5.resteventhub.StrategyManager;
import com.lab5.resteventhub.strategy.AbstractStrategy;
import com.lab5.resteventhub.web.rest.dto.RequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLController {

    private StrategyManager strategyManager;

    public URLController(StrategyManager strategyManager) {
        this.strategyManager = strategyManager;
    }

    @PostMapping("/url")
    public void addNewUrl(@RequestBody RequestDTO dto) {
        AbstractStrategy sendDataStrategy = this.strategyManager.getStrategy(dto.getStrategy());
        try {
            sendDataStrategy.fetchFromAndTransfer(dto.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}