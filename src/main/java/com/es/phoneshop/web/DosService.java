package com.es.phoneshop.web;

import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosService implements DosProtectionService {

    private final int MAX_AVAILABLE_REQUESTS = 2000;

    private final long MAX_AVAILABLE_TIME = 10;

    private static DosService dosService;

    private Map<String, Pair<Long,Integer>> requestCountMap;

    private DosService() {
        requestCountMap = new ConcurrentHashMap<>();
    }

    public static DosService getInstance() {
        if (dosService == null) {
            dosService = new DosService();
        }
        return dosService;
    }


    @Override
    public boolean allowed(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Pair<Long, Integer> counter = requestCountMap.get(ip);
        if (counter == null || counter.getKey() > MAX_AVAILABLE_TIME) {
            counter = new Pair<>(Long.valueOf(0), 0);
            requestCountMap.put(ip, counter);
            return true;
        } else if (counter.getValue() > MAX_AVAILABLE_REQUESTS) {
            return false;
        }
        long spentTime = new Date().getTime() + counter.getKey();
        Integer count = counter.getValue();
        requestCountMap.put(ip, new Pair<>(spentTime, count++));
        return true;
    }
}
