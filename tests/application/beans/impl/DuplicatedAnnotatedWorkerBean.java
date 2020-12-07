package application.beans.impl;

import application.TestOutput;
import application.beans.AnnotatedWorkerBean;

public class DuplicatedAnnotatedWorkerBean implements AnnotatedWorkerBean {

    private TestOutput testOutput;

    public DuplicatedAnnotatedWorkerBean(TestOutput testOutput) {
        this.testOutput = testOutput;
    }

    @Override
    public void doWork() {
        testOutput.print("do second work");
    }


}
