package common.transaction;

import common.annotation.MicroTransactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class TransactionInvocationHandler implements InvocationHandler {

    private static final Logger logger = Logger.getLogger(TransactionInvocationHandler.class.getName());

    private TransactionProvider transactionProvider;
    private Object target;

    public TransactionInvocationHandler(Object target, TransactionProvider transactionProvider) {
        this.target = target;
        this.transactionProvider = transactionProvider;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(MicroTransactional.class)) {
            if (transactionProvider != null) {
                transactionProvider.open();
                Object result = method.invoke(target, args);
                transactionProvider.close();
                return result;
            }
            else {
                logger.warning("TransactionalProvider is not installed");
            }
        }
        return method.invoke(target, args);
    }

}
