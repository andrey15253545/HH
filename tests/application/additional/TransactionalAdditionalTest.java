package application.additional;

import application.TestOutput;
import application.TestTransactionProvider;
import application.additional.beans.*;
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
        Context context = new EasyContext();
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));

        MultiImplementedBean multiImplementedBean = new MultiImplementedBean(testOutput);

        context.addBean(multiImplementedBean);

        NotAnnotatedWorkerBean notAnnotatedWorkerBeanFromContext = (NotAnnotatedWorkerBean) context.getBean(NotAnnotatedWorkerBean.class);
        AnnotatedWorkerBean annotatedWorkerBeanFromContext = (AnnotatedWorkerBean) context.getBean(AnnotatedWorkerBean.class);

        annotatedWorkerBeanFromContext.doWork();
        assertEquals(List.of("open transaction", "do some work", "close transaction"), testOutput.getRows());

        notAnnotatedWorkerBeanFromContext.doWork();
        assertEquals(List.of("open transaction", "do some work", "close transaction", "do some work"), testOutput.getRows());

    }

    @Test
    public void multipleMethods() {
        TestOutput testOutput = new TestOutput();
        Context context = new EasyContext();
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));

        context.addBean(new MultipleAnnotatedMethodsBeanImpl(testOutput));

        MultipleAnnotatedMethodsBean multipleAnnotatedMethodsBean =
                (MultipleAnnotatedMethodsBean) context.getBean(MultipleAnnotatedMethodsBean.class);

        multipleAnnotatedMethodsBean.doOneWork();
        multipleAnnotatedMethodsBean.doTwoWork();

        assertEquals(
                List.of(
                        "open transaction",
                        "do one work",
                        "close transaction",
                        "open transaction",
                        "do two work",
                        "close transaction"
                ), testOutput.getRows())
        ;
    }

    @Test
    public void withSameMethodNames() {
        TestOutput testOutput = new TestOutput();
        Context context = new EasyContext();
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));

        context.addBean(new WithSameMethodNamesBeanImpl(testOutput));
        WithSameMethodNamesBean withSameMethodNamesBean = (WithSameMethodNamesBean) context.getBean(WithSameMethodNamesBean.class);
        withSameMethodNamesBean.doWork();
        withSameMethodNamesBean.doWork("additional work");

        assertEquals(
                List.of(
                        "open transaction",
                        "do work",
                        "close transaction",
                        "open transaction",
                        "do work and additional work",
                        "close transaction"
                ), testOutput.getRows())
        ;


    }


}
