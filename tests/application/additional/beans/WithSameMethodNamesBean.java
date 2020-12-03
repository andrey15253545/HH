package application.additional.beans;

import common.MicroTransactional;

public interface WithSameMethodNamesBean {

    @MicroTransactional
    void doWork();

    @MicroTransactional
    void doWork(String additionalWork);

}
