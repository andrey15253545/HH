package common.storage.impl;

import common.exception.AdditionSameBeanException;
import common.exception.InstanceNotExistException;
import common.storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class EasyStorage implements Storage {

    private final Map<Class, Object> beanMap = new HashMap<>();

    @Override
    public void add(Class clazz, Object object) {
        if (beanMap.containsKey(clazz)) {
            throw new AdditionSameBeanException(clazz);
        }
        beanMap.put(clazz, object);
    }

    @Override
    public Object get(Class beanClass) {
        if (!beanMap.containsKey(beanClass)) {
            throw new InstanceNotExistException(beanClass);
        }
        return beanMap.get(beanClass);
    }
}
