package com.lab5.resteventhub.service.impl;

import com.lab5.resteventhub.service.SendDataBehaviour;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.time.Instant;

@Service
public class SendDataConsoleBehaviourImpl implements SendDataBehaviour {

    private static final boolean USE_SSL = true;
    private static final int MAX_NUMBER = 1000;

    private final static String CACHE_HOSTNAME = "labfive.redis.cache.windows.net";
    private final static String CACHE_KEY = "iovSQzla3dSI5lIjorZjG4MpEsIMQIKTuBdjTu1Kv7E=";
    private final static String MAP_NAME = "ConsoleLog";
    private final static int PORT = 6380;

    public void sendAndLog(JSONArray jsonArray) {
        JedisShardInfo info = new JedisShardInfo(CACHE_HOSTNAME, PORT, USE_SSL);
        info.setPassword(CACHE_KEY);
        Jedis jedis = new Jedis(info);

        jedis.hset(MAP_NAME, "Raws", "" + jsonArray.length());
        String tempFileName = "LOG_" + Instant.now();
        if (jsonArray.length() != MAX_NUMBER) {
            System.out.println("Raws from file " + "'" + tempFileName + "': " + jedis.get("Raws"));
            jedis.hset(MAP_NAME, tempFileName, jsonArray.toString());

            jedis.hset(MAP_NAME, "Status", "NotFinished");
        } else {
            System.out.println("Raws from file " + "'" + tempFileName + "': " + jedis.get("Raws"));
            jedis.hset(MAP_NAME, "Raws", "" + jsonArray.length());
            jedis.hset(MAP_NAME, "Status", "Completed");
            jedis.hset(MAP_NAME, "Info", "First attempt to input this file");
        }

        jedis.close();
    }
}
