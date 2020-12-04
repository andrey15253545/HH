package common.context.impl;

import common.transaction.TransactionProvider;
import common.context.Context;
import common.transaction.TransactionInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static common.constants.ExceptionMessage.INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE;

/**
 * Имплементация нашего контекста
 * TODO
 */
public class MultiImplementContext implements Context {

    private TransactionProvider transactionProvider = null;

    private final Map<Class, Set<Object>> beanMap = new HashMap<>();

    @Override
    public void addBean(Object bean) {
        if (bean == null) {
            throw new IllegalArgumentException(INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE);
        }
        addPairClassImplementation(bean.getClass(), bean);
        for (Class<?> anInterface : bean.getClass().getInterfaces()) {
            Object newBean = Proxy.newProxyInstance(
                    EasyContext.class.getClassLoader(),
                    new Class[]{anInterface},
                    new TransactionInvocationHandler(bean, transactionProvider)
            );
            addPairClassImplementation(anInterface, newBean);
        }
    }

    @Override
    public Object getBean(Class beanClass) {
        if (!beanMap.containsKey(beanClass)) {
            return null;
        }
        Set<Object> objects = beanMap.get(beanClass);
        if (objects.size() == 1) {
            return objects.toArray()[0];
        }
        return objects;
    }

    @Override
    public void setTransactionalProvider(TransactionProvider transactionProvider) {
        this.transactionProvider = transactionProvider;
    }

    private void addPairClassImplementation(Class clazz, Object object) {
        if (!beanMap.containsKey(clazz)) {
            beanMap.put(clazz, new HashSet<>());
        }
        beanMap.get(clazz).add(object);
    }

}

