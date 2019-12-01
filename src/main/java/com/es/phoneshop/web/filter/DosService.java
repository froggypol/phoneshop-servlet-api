package com.es.phoneshop.web.filter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosService implements DosProtectionService {

    private final int MAX_AVAILABLE_REQUESTS = 2000;

    private static DosService dosService;

    private Integer counter;

    private Map<String, Integer> requestCountMap;

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
        counter = requestCountMap.get(ip);
        if (counter == null) {
            requestCountMap.put(ip, 0);
            counter = 0;
        } else if (counter > MAX_AVAILABLE_REQUESTS) {
            return false;
        }
        requestCountMap.put(ip, counter++);
        return true;
    }
}
