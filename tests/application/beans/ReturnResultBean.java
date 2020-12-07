package application.beans;

import common.annotation.MicroTransactional;

public interface ReturnResultBean {

    @MicroTransactional
    int sum(int x, int y);

    String sum(String s1, String s2);
}
