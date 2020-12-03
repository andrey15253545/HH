package application.additional.beans;

import application.TestOutput;
import application.beans.AnnotatedWorkerBean;
import application.beans.NotAnnotatedWorkerBean;

public class MultiImplementedBean implements AnnotatedWorkerBean, NotAnnotatedWorkerBean {

    private TestOutput testOutput;

    public MultiImplementedBean(TestOutput testOutput) {
        this.testOutput = testOutput;
    }

    @Override
    public void doWork() {
        testOutput.print("do some work");
    }
}
