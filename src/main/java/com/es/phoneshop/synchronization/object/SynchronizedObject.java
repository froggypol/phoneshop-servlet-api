package com.es.phoneshop.synchronization.object;

import java.util.HashMap;
import java.util.Map;

public class SynchronizedObject {

    private static Map<String, Object> objectPool = new HashMap<>();

    public static Object getSynchronizedObject(String idToCheck) {
        Object object;
        if (objectPool.containsKey(idToCheck)) {
            return returnObjectFromPool(idToCheck);
        }
        else {
            object = new Object();
            objectPool.put(idToCheck, object);
            return object;
        }
    }

    private static Object returnObjectFromPool(String idToCheck) {
        return objectPool.get(idToCheck);
    }
}
