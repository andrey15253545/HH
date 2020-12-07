package application.beans.impl;

import application.TestOutput;
import application.beans.NotAnnotatedWorkerBean;

public class NotAnnotatedWorkerBeanImpl implements NotAnnotatedWorkerBean {
  private final TestOutput output;

  public NotAnnotatedWorkerBeanImpl(TestOutput output) {
    this.output = output;
  }

  public void doWork() {
    output.print("doWork");
  }
}
