package common.invocation;

import common.MicroTransactional;
import common.TransactionProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TransactionInvocationHandler implements InvocationHandler {

    private TransactionProvider transactionProvider;
    private Object target;

    public TransactionInvocationHandler(Object target, TransactionProvider transactionProvider) {
        this.target = target;
        this.transactionProvider = transactionProvider;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(MicroTransactional.class)) {
            transactionProvider.open();
            Object result = method.invoke(target, args);
            transactionProvider.close();
            return result;
        }
        else
            return method.invoke(target, args);
    }
}
