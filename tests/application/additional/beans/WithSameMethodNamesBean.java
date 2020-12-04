package application.additional.beans;

import common.annotation.MicroTransactional;

public interface WithSameMethodNamesBean {

    @MicroTransactional
    void doWork();

    @MicroTransactional
    void doWork(String additionalWork);

    void doWork(int x);

}
