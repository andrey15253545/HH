package common.exception;

import static common.constants.ExceptionMessage.SAME_BEAN_EXCEPTION_MESSAGE_FORMAT;

public class AdditionSameBeanException extends RuntimeException {

    public AdditionSameBeanException(Class classes) {
        super(String.format(SAME_BEAN_EXCEPTION_MESSAGE_FORMAT, classes.toString()));
    }

}
