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
import common.context.Context;
import common.context.impl.EasyContext;
import common.exception.AdditionSameBeanException;
import common.exception.InstanceNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdditionalTransactionalTest {

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

        //checking that transactions are executed only in annotated methods
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

        //checking that transactions are executed on more than one method
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
        withSameMethodNamesBean.doWork(15);

        //checks that transactions are executed independently of the method name
        assertEquals(
                List.of(
                        "open transaction", "do work", "close transaction",
                        "open transaction", "do work and additional work", "close transaction",
                        "15"
                ),
                testOutput.getRows())
        ;
    }

    @Test
    public void ReturnResult() {
        context.addBean(new ReturnResultBeanImpl());
        ReturnResultBean returnResultBean = (ReturnResultBean) context.getBean(ReturnResultBean.class);

        String result = returnResultBean.sum("very ", "hard");

        //return result check
        assertEquals("very hard", result);
        //checking that transaction were not executed
        assertEquals(Collections.emptyList(), testOutput.getRows());

        int n = returnResultBean.sum(5, 10);

        //return resu   lt check
        assertEquals(15, n);
        //transaction executed check
        assertEquals(List.of("open transaction", "close transaction"), testOutput.getRows());
    }


    //TODO
    @Test
    public void multipleInstance() {
        context.addBean(new AnnotatedWorkerBeanImpl(this.testOutput));

        //checking that exception was thrown when added two implementation of interface
        assertThrows(
                AdditionSameBeanException.class,
                () -> context.addBean(new DuplicatedAnnotatedWorkerBean(this.testOutput))
        );

    }

    @Test
    public void nonExistsBean() {
        context.addBean(new AnnotatedWorkerBeanImpl(this.testOutput));

        //checking that exception was thrown when trying to get a nonexistent bean in the context
        assertThrows(
                InstanceNotExistException.class,
                () -> context.getBean(NotAnnotatedWorkerBean.class)
        );
    }

}
