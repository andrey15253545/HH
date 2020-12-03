package application.additional.beans;

import common.MicroTransactional;

public interface MultipleAnnotatedMethodsBean {

    @MicroTransactional
    void doOneWork();

    @MicroTransactional
    void doTwoWork();

}
