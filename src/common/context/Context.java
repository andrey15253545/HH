package common.context;

import common.transaction.TransactionProvider;

/**
 * Контекст для нашего мини-спринга
 */
public interface Context {

    void addBean(Object bean);

    Object getBean(Class beanClass);

    void setTransactionalProvider(TransactionProvider transactionProvider);

}
