package common.context.impl;

import common.context.Context;
import common.exception.AdditionSameBeanException;
import common.exception.InstanceNotExistException;
import common.storage.Storage;
import common.storage.impl.EasyStorage;
import common.transaction.TransactionInvocationHandler;
import common.transaction.TransactionProvider;

import java.lang.reflect.Proxy;

import static common.constants.ExceptionMessage.INPUT_INSTANCE_NULL_EXCEPTION_MESSAGE;

/**
 * Context implementation
 * Allows to store only one implementation of a specific type {@link EasyStorage}
 */
public class EasyContext implements Context {

    private TransactionProvider transactionProvider = null;

    protected Storage storage = new EasyStorage();

    /**
     * Adds an object to context
     * If transaction provider {@link TransactionProvider} was sets,
     * creates a new instance of the implemented interfaces
     * before adding them to the storage {@link Storage#add(Class, Object)}
     * In the new instance before calling the method executes opening of trwansaction
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
        this.storage.add(bean.getClass(), bean);
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            if (this.transactionProvider != null) {
                Object newBean = Proxy.newProxyInstance(
                        EasyContext.class.getClassLoader(),
                        new Class[]{anInterface},
                        new TransactionInvocationHandler(bean, this.transactionProvider)
                );
                this.storage.add(anInterface, newBean);
            }
            else {
                this.storage.add(anInterface, bean);
            }
        }
    }

    /**
     * Returns a bean from storage {@link Storage}
     *
     * @param beanClass  bean type that will return
     * @return instance of requested type
     * @throws InstanceNotExistException if an instance of requested type was not added
     */
    @Override
    public Object getBean(Class beanClass) {
        return this.storage.get(beanClass);
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


}