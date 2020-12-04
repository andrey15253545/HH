package common.exception;

import java.util.List;

public class AdditionSameBeanException extends RuntimeException {

    private static final String SAME_BEAN_EXCEPTION_FORMAT = "Bean types %s already exists in context";

    public AdditionSameBeanException(List<Class> classes) {
        super(String.format(SAME_BEAN_EXCEPTION_FORMAT, classes.toString()));
    }

}
