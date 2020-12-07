package application.beans.impl;

import application.beans.ReturnResultBean;

public class ReturnResultBeanImpl implements ReturnResultBean {

    @Override
    public int sum(int x, int y) {
        return x + y;
    }

    @Override
    public String sum(String s1, String s2) {
        return s1 + s2;
    }


}
