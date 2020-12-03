package application.beans;

import common.MicroTransactional;

public interface AnnotatedWorkerBean {
  @MicroTransactional
  void doWork();
}
