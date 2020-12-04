package common.exception;

import java.util.List;

import static common.constants.ExceptionMessage.SAME_BEAN_EXCEPTION_MESSAGE_FORMAT;

public class AdditionSameBeanException extends RuntimeException {

    public AdditionSameBeanException(List<Class> classes) {
        super(String.format(SAME_BEAN_EXCEPTION_MESSAGE_FORMAT, classes.toString()));
    }

}
