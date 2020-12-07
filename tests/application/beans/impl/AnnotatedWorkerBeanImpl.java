package application.beans.impl;

import application.TestOutput;
import application.beans.AnnotatedWorkerBean;

public class  AnnotatedWorkerBeanImpl implements AnnotatedWorkerBean {
  private final TestOutput output;

  public AnnotatedWorkerBeanImpl(TestOutput output) {
    this.output = output;
  }

  public void doWork() {
    output.print("doWork");
  }
}
