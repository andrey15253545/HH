package application.beans;

import common.annotation.MicroTransactional;

public interface MultipleAnnotatedMethodsBean {

    @MicroTransactional
    void doOneWork();

    @MicroTransactional
    void doTwoWork();

}
