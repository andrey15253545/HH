import application.TestOutput;
import application.TestTransactionProvider;
import application.beans.NotAnnotatedWorkerBean;
import application.beans.impl.AnnotatedWorkerBeanImpl;
import application.beans.impl.DuplicatedAnnotatedWorkerBean;
import common.context.Context;
import common.context.impl.EasyContext;
import common.exception.AdditionSameBeanException;
import common.exception.InstanceNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdditionalEasyContextTest {

    private TestOutput testOutput;
    private Context context;

    @BeforeEach
    public void before() {
        testOutput = new TestOutput();
        context = new EasyContext();
        context.setTransactionalProvider(new TestTransactionProvider(testOutput));
    }

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
