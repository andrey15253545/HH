package common.context.impl;

import common.transaction.TransactionProvider;
import common.annotation.MicroTransactional;
import common.context.Context;
import common.exception.AdditionSameBeanException;
import common.transaction.TransactionInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.constants.ExceptionMessage.INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE;

/**
 * Имплементация нашего контекста
 * Allows to store only one implementation of a specific type
 */
public class EasyContext implements Context {

    private TransactionProvider transactionProvider = null;

    private final Map<Class, Object> beanMap = new HashMap<>();

    /**
     * Adds an object to context
     * If transaction provider {@link TransactionProvider} was sets,
     * creates a new instance of the implemented interfaces
     * before adding them to bean map {@link EasyContext#beanMap}
     * In the new instance before calling the method executes opening of transaction
     * and after the call is executes a closing of transactional {@link TransactionInvocationHandler}
     *
     * @param bean  instance to be added to the context
     * @throws IllegalArgumentException if the input instance is null
     * @throws AdditionSameBeanException if an instance of this class or its interfaces already exists
     */
    @Override
    public void addBean(Object bean) {
        if (bean == null) {
            throw new IllegalArgumentException(INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE);
        }
        checkSameBean(bean);
        beanMap.put(bean.getClass(), bean);
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            if (transactionProvider != null) {
                Object newBean = Proxy.newProxyInstance(
                        EasyContext.class.getClassLoader(),
                        new Class[]{anInterface},
                        new TransactionInvocationHandler(bean, transactionProvider)
                );
                beanMap.put(anInterface, newBean);
            }
            else {
                beanMap.put(anInterface, bean);
            }
        }
    }

    /**
     * Returns a bean with transactional methods,
     * if they were annotated {@link MicroTransactional}
     *
     * @param beanClass  bean type that will return
     * @return object of requested type or null if it is not present in the context
     */
    @Override
    public Object getBean(Class beanClass) {
        return beanMap.get(beanClass);
    }

    /**
     * Sets the transaction provider that is executed when calling annotated methods
     *
     * @param transactionProvider transaction provider to set
     */
    @Override
    public void setTransactionalProvider(TransactionProvider transactionProvider) {
        this.transactionProvider = transactionProvider;
    }

    private void checkSameBean(Object bean) {
        List<Class> existingTypes = new ArrayList<>();
        if (beanMap.containsKey(bean.getClass())) {
            existingTypes.add(bean.getClass());
        }
        for (Class<?> anInterface : bean.getClass().getInterfaces()) {
            if (beanMap.containsKey(anInterface)) {
                existingTypes.add(anInterface);
            }
        }
        if (existingTypes.size() != 0) {
            throw new AdditionSameBeanException(existingTypes);
        }
    }

}