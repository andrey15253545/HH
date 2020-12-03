package common;

/**
 * Имплементация нашего контекста
 * TODO
 */
public class EasyContext implements Context {

  private TransactionProvider transactionProvider = null;

  @Override
  public void addBean(Object bean) {
    TODO
  }

  @Override
  public Object getBean(Class beanClass) {
    TODO
  }

  @Override
  public void setTransactionalProvider(TransactionProvider transactionProvider) {
    this.transactionProvider = transactionProvider;
  }
}
