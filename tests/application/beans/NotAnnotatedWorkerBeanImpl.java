package application.beans;

import application.TestOutput;

public class NotAnnotatedWorkerBeanImpl implements NotAnnotatedWorkerBean {
  private final TestOutput output;

  public NotAnnotatedWorkerBeanImpl(TestOutput output) {
    this.output = output;
  }

  public void doWork() {
    output.print("doWork");
  }
}
