package common.storage.impl;

import common.storage.Storage;

import java.util.*;

public class MultipleImplementationsStorage implements Storage {

    private Map<Class, Set<Object>> beanMap = new HashMap<>();

    @Override
    public void add(Class clazz, Object object) {
        if (!beanMap.containsKey(clazz)) {
            beanMap.put(clazz, new HashSet<>());
        }
        beanMap.get(clazz).add(object);
    }

    @Override
    public Set<Object> get(Class clazz) {
        return beanMap.get(clazz);
    }

}
