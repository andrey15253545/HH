package application.additional.beans.impl;

import application.additional.beans.ReturnResultBean;

public class ReturnResultBeanImpl implements ReturnResultBean {

    @Override
    public int doWork(int n) {
        return 0;
    }

    @Override
    public String doWork(String s) {
        return "do " + s + " work";
    }


}
