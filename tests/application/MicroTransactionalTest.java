package application;

import application.beans.AnnotatedWorkerBean;
import application.beans.AnnotatedWorkerBeanImpl;
import application.beans.NotAnnotatedWorkerBean;
import application.beans.NotAnnotatedWorkerBeanImpl;
import common.context.Context;
import common.context.impl.EasyContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Тесты транзакционность
 */
class MicroTransactionalTest {
  @Test
  public void testMicroTransactional() {
    Context appContext = new EasyContext();

    TestOutput output = new TestOutput();
    TestTransactionProvider testTransactionProvider = new TestTransactionProvider(output);

    appContext.setTransactionalProvider(testTransactionProvider);
    appContext.addBean(new AnnotatedWorkerBeanImpl(output));

    AnnotatedWorkerBean annotatedBean = (AnnotatedWorkerBean)appContext.getBean(AnnotatedWorkerBean.class);
    annotatedBean.doWork();
    // Вызов транзакционный:
    assertEquals(List.of("open transaction","doWork", "close transaction"), output.getRows());
  }

  @Test
  public void testMicroNoTransactional() {
    Context appContext = new EasyContext();

    TestOutput output = new TestOutput();
    TestTransactionProvider testTransactionProvider = new TestTransactionProvider(output);

    appContext.setTransactionalProvider(testTransactionProvider);
    appContext.addBean(new NotAnnotatedWorkerBeanImpl(output));

    NotAnnotatedWorkerBean notAnnotatedBean = (NotAnnotatedWorkerBean)appContext.getBean(NotAnnotatedWorkerBean.class);
    notAnnotatedBean.doWork();
    // Вызов не транзакционный:
    assertEquals(List.of("doWork"), output.getRows());
  }
}