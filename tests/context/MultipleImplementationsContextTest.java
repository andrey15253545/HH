package context;

import application.TestOutput;
import application.TestTransactionProvider;
import application.additional.beans.impl.DuplicatedAnnotatedWorkerBean;
import application.beans.AnnotatedWorkerBean;
import application.beans.AnnotatedWorkerBeanImpl;
import common.context.Context;
import common.context.impl.MultipleImplementationsContext;
import common.storage.impl.MultipleImplementationsStorage;
import common.transaction.TransactionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleImplementationsContextTest {

    private TestOutput testOutput;
    private Context context;

    @BeforeEach
    public void before() {
        testOutput = new TestOutput();
        context = new MultipleImplementationsContext(new MultipleImplementationsStorage());
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));
    }

    @Test
    public void test() {
        //adding a first bean
        context.addBean(new AnnotatedWorkerBeanImpl(this.testOutput));

        TestOutput secondTestOutput = new TestOutput();
        TransactionProvider secondTransactionProvider = new TestTransactionProvider(secondTestOutput);
        context.setTransactionalProvider(secondTransactionProvider);

        //adding a second bean
        context.addBean(new DuplicatedAnnotatedWorkerBean(secondTestOutput));

        Set<AnnotatedWorkerBean> beans = (Set<AnnotatedWorkerBean>) context.getBean(AnnotatedWorkerBean.class);
        assertEquals(2, beans.size());

        beans.forEach(AnnotatedWorkerBean::doWork);

        assertEquals(List.of("open transaction", "doWork", "close transaction"), testOutput.getRows());
        assertEquals(List.of("open transaction", "do second work", "close transaction"), secondTestOutput.getRows());
    }

}
