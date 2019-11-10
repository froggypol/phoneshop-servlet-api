package com.es.phoneshop.synchronization.object;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SynchronizedObject {

    private Map<String, Object> objectPool = new HashMap<>();

    public SynchronizedObject(String idToCheck) {
        Object object;
        if (objectPool.containsKey(idToCheck)) {
            returnObjectFromPool(idToCheck);
        } else {
            object = new Object();
            objectPool.put(UUID.randomUUID().toString(), object);
        }
    }

    private Object returnObjectFromPool(String idToCheck) {
        return objectPool.get(idToCheck);
    }
}
