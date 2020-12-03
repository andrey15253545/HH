package common;

import common.invocation.TransactionInvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Имплементация нашего контекста
 * TODO
 */
public class EasyContext implements Context {

    private TransactionProvider transactionProvider = null;

    private final Map<Class, Object> beanMap = new HashMap<>();

    @Override
    public void addBean(Object bean) { //TODO check who implementation
        beanMap.put(bean.getClass(), bean);
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            Object newBean = Proxy.newProxyInstance(
                    EasyContext.class.getClassLoader(),
                    new Class[]{anInterface},
                    new TransactionInvocationHandler(bean, transactionProvider)
            );
            beanMap.put(anInterface, newBean);
        }
    }

    @Override
    public Object getBean(Class beanClass) {
        return beanMap.get(beanClass);
    }

    @Override
    public void setTransactionalProvider(TransactionProvider transactionProvider) {
        this.transactionProvider = transactionProvider;
    }

}
