package application.beans;

import application.TestOutput;

public class  AnnotatedWorkerBeanImpl implements AnnotatedWorkerBean {
  private final TestOutput output;

  public AnnotatedWorkerBeanImpl(TestOutput output) {
    this.output = output;
  }

  public void doWork() {
    output.print("doWork");
  }
}
