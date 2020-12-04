package application.additional;

import application.TestOutput;
import application.TestTransactionProvider;
import application.additional.beans.MultiImplementedBean;
import application.additional.beans.MultipleAnnotatedMethodsBean;
import application.additional.beans.ReturnResultBean;
import application.additional.beans.WithSameMethodNamesBean;
import application.additional.beans.impl.DuplicatedAnnotatedWorkerBean;
import application.additional.beans.impl.MultipleAnnotatedMethodsBeanImpl;
import application.additional.beans.impl.ReturnResultBeanImpl;
import application.additional.beans.impl.WithSameMethodNamesBeanImpl;
import application.beans.AnnotatedWorkerBean;
import application.beans.AnnotatedWorkerBeanImpl;
import application.beans.NotAnnotatedWorkerBean;
import common.Context;
import common.EasyContext;
import common.TransactionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TransactionalAdditionalTest {

    private TestOutput testOutput;
    private Context context;


    @BeforeEach
    public void before() {
        testOutput = new TestOutput();
        context = new EasyContext();
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));
    }


    @Test
    public void multipleInterfaces() {

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

        context.addBean(new MultipleAnnotatedMethodsBeanImpl(testOutput));

        MultipleAnnotatedMethodsBean multipleAnnotatedMethodsBean =
                (MultipleAnnotatedMethodsBean) context.getBean(MultipleAnnotatedMethodsBean.class);

        multipleAnnotatedMethodsBean.doOneWork();
        multipleAnnotatedMethodsBean.doTwoWork();

        assertEquals(
                List.of(
                        "open transaction", "do one work", "close transaction",
                        "open transaction", "do two work", "close transaction"
                ),
                testOutput.getRows());
    }

    @Test
    public void withSameMethodNames() {

        context.addBean(new WithSameMethodNamesBeanImpl(testOutput));
        WithSameMethodNamesBean withSameMethodNamesBean = (WithSameMethodNamesBean) context.getBean(WithSameMethodNamesBean.class);
        withSameMethodNamesBean.doWork();
        withSameMethodNamesBean.doWork("additional work");

        assertEquals(
                List.of(
                        "open transaction", "do work", "close transaction",
                        "open transaction", "do work and additional work", "close transaction"
                ),
                testOutput.getRows())
        ;
    }

    @Test
    public void ReturnResult() {
        context.addBean(new ReturnResultBeanImpl());
        ReturnResultBean returnResultBean = (ReturnResultBean) context.getBean(ReturnResultBean.class);
        String result = returnResultBean.doWork("very hard");
        assertEquals(Collections.emptyList(), testOutput.getRows());
        assertEquals("do very hard work", result);

        int n = returnResultBean.doWork(5);
        assertEquals(List.of("open transaction", "close transaction"), testOutput.getRows());
        assertEquals(0, n);
    }

    @Test
    public void multipleInstance() {
        context.addBean(new AnnotatedWorkerBeanImpl(this.testOutput));

        TestOutput secondTestOutput = new TestOutput();
        TransactionProvider secondTransactionProvider = new TestTransactionProvider(secondTestOutput);

        context.setTransactionalProvider(secondTransactionProvider);
        context.addBean(new DuplicatedAnnotatedWorkerBean(secondTestOutput));

        Set<AnnotatedWorkerBean> beans = (Set<AnnotatedWorkerBean>) context.getBean(AnnotatedWorkerBean.class);
        assertEquals(2, beans.size());

        beans.forEach(AnnotatedWorkerBean::doWork);

        assertEquals(List.of("open transaction", "doWork", "close transaction"), testOutput.getRows());
        assertEquals(List.of("open transaction", "do second work", "close transaction"), secondTestOutput.getRows());


    }

}
