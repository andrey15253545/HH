package common.invocation;

import common.TransactionProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TransactionInvocationHandler implements InvocationHandler {

    private TransactionProvider transactionProvider;
    private final Map<String, Method> methods = new HashMap<>();
    private Object target;

    public TransactionInvocationHandler(Object target, TransactionProvider transactionProvider) {
        this.target = target;
        for(Method method: target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
        this.transactionProvider = transactionProvider;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        transactionProvider.open();
        Object result = methods.get(method.getName()).invoke(target, args);
        transactionProvider.close();
        return result;
    }
}
