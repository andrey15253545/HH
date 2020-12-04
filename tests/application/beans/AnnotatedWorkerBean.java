package application.beans;

import common.annotation.MicroTransactional;

public interface AnnotatedWorkerBean {
  @MicroTransactional
  void doWork();
}
