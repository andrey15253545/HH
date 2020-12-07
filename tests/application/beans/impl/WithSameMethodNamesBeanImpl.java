package application.beans.impl;

import application.TestOutput;
import application.beans.WithSameMethodNamesBean;

public class WithSameMethodNamesBeanImpl implements WithSameMethodNamesBean {

    private TestOutput testOutput;

    public WithSameMethodNamesBeanImpl(TestOutput testOutput) {
        this.testOutput = testOutput;
    }

    @Override
    public void doWork() {
        testOutput.print("do work");
    }

    @Override
    public void doWork(String additionalWork) {
        testOutput.print("do work and " + additionalWork);
    }

    @Override
    public void doWork(int x) {
        testOutput.print(String.valueOf(x));
    }
}
