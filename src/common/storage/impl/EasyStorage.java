package common.storage.impl;

import common.exception.AdditionSameBeanException;
import common.exception.InstanceNotExistException;
import common.storage.Storage;

import java.util.HashMap;
import java.util.Map;

import static common.constants.ExceptionMessage.INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE;
import static common.constants.ExceptionMessage.REQUESTED_TYPE_NULL_EXCEPTION_MESSAGE;

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
        if (object == null) {
            throw new IllegalArgumentException(INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE);
        }
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
     * @throws IllegalArgumentException if try to get a bean by null
     */
    @Override
    public Object get(Class beanClass) {
        if (beanClass == null) {
            throw new IllegalArgumentException(REQUESTED_TYPE_NULL_EXCEPTION_MESSAGE);
        }
        if (!beanMap.containsKey(beanClass)) {
            throw new InstanceNotExistException(beanClass);
        }
        return beanMap.get(beanClass);
    }
}
