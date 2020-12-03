package application.additional.beans;

import common.MicroTransactional;

public interface ReturnResultBean {

    @MicroTransactional
    int doWork(int n);

    String doWork(String s);

}
