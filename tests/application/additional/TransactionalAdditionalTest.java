package application.additional;

import application.TestOutput;
import application.TestTransactionProvider;
import application.additional.beans.MultiImplementedBean;
import application.beans.AnnotatedWorkerBean;
import application.beans.NotAnnotatedWorkerBean;
import common.Context;
import common.EasyContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionalAdditionalTest {

    @Test
    public void multipleInterfaces() {
        TestOutput testOutput = new TestOutput();
        MultiImplementedBean multiImplementedBean = new MultiImplementedBean(testOutput);

        Context context = new EasyContext();
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));

        context.addBean(multiImplementedBean);

        NotAnnotatedWorkerBean notAnnotatedWorkerBeanFromContext = (NotAnnotatedWorkerBean)context.getBean(NotAnnotatedWorkerBean.class);
        AnnotatedWorkerBean annotatedWorkerBeanFromContext = (AnnotatedWorkerBean)context.getBean(AnnotatedWorkerBean.class);

        annotatedWorkerBeanFromContext.doWork();
        assertEquals(List.of("open transaction","do some work", "close transaction"), testOutput.getRows());

        notAnnotatedWorkerBeanFromContext.doWork();
        assertEquals(List.of("open transaction","do some work", "close transaction","do some work"), testOutput.getRows());

    }



}
