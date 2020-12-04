package common.transaction;

import common.annotation.MicroTransactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Logger;

import static common.constants.ExceptionMessage.TARGET_OBJECT_NPE_MESSAGE;
import static common.constants.ExceptionMessage.TRANSACTION_PROVIDER_NPE_MESSAGE;

public class TransactionInvocationHandler implements InvocationHandler {

    private static final Logger logger = Logger.getLogger(TransactionInvocationHandler.class.getName());

    private TransactionProvider transactionProvider;
    private Object target;

    public TransactionInvocationHandler(Object target, TransactionProvider transactionProvider) {
        this.target = Objects.requireNonNull(target, TARGET_OBJECT_NPE_MESSAGE);
        this.transactionProvider = Objects.requireNonNull(transactionProvider, TRANSACTION_PROVIDER_NPE_MESSAGE);
    }

    /**
     *
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
