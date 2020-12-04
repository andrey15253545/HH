package common.storage.impl;

import common.exception.AdditionSameBeanException;
import common.exception.InstanceNotExistException;
import common.storage.Storage;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows to store only one implementation of a specific type
 */
public class EasyStorage implements Storage {

    private final Map<Class, Object> beanMap = new HashMap<>();


    /**
     * Adds bean to storage
     *
     * @param clazz  type of class to add to the storage
     * @param object  instance to add to the storage
     *
     * @throws AdditionSameBeanException if an instance of the class being added exists
     */
    @Override
    public void add(Class clazz, Object object) {
        if (beanMap.containsKey(clazz)) {
            throw new AdditionSameBeanException(clazz);
        }
        beanMap.put(clazz, object);
    }


    /**
     * Returns the stored instance by type
     *
     * @param beanClass type of requested instance
     * @return instance of requested type
     * @throws InstanceNotExistException if requested instance is not exists in the storage
     */
    @Override
    public Object get(Class beanClass) {
        if (!beanMap.containsKey(beanClass)) {
            throw new InstanceNotExistException(beanClass);
        }
        return beanMap.get(beanClass);
    }
}
