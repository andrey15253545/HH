package application.beans.impl;

import application.TestOutput;
import application.beans.MultipleAnnotatedMethodsBean;

public class MultipleAnnotatedMethodsBeanImpl implements MultipleAnnotatedMethodsBean {

    private TestOutput testOutput;

    public MultipleAnnotatedMethodsBeanImpl(TestOutput testOutput) {
        this.testOutput = testOutput;
    }

    @Override
    public void doOneWork() {
        testOutput.print("do one work");
    }

    @Override
    public void doTwoWork() {
        testOutput.print("do two work");
    }
}
