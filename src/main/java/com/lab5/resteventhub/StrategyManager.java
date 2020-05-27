package com.lab5.resteventhub;

import com.lab5.resteventhub.strategy.AbstractStrategy;
import com.lab5.resteventhub.strategy.SendDataConsoleStrategy;
import com.lab5.resteventhub.strategy.SendDataEventHubStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StrategyManager {

    private Map<String, AbstractStrategy> strategyMap;

    public StrategyManager() {
        strategyMap = createStrategies();
    }

    public AbstractStrategy getStrategy(String repositoryName) {
        return strategyMap.get(repositoryName);
    }

    public Set<String> getAllNames() {
        return strategyMap.keySet();
    }

    public Collection<AbstractStrategy> getAllStrategies(){
        return strategyMap.values();
    }

    private Map<String, AbstractStrategy> createStrategies(){
        Map<String, AbstractStrategy> strategies = new HashMap<>();
        strategies.put("eventHub", new SendDataEventHubStrategy());
        strategies.put("redis", new SendDataConsoleStrategy());
        return strategies;
    }
}