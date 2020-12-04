package common.exception;

import static common.constants.ExceptionMessage.INSTANCE_NOT_EXISTS_EXCEPTION_MESSAGE_FORMAT;

public class InstanceNotExistException extends RuntimeException {

    public InstanceNotExistException(Class clazz) {
        super(String.format(INSTANCE_NOT_EXISTS_EXCEPTION_MESSAGE_FORMAT, clazz.getName()));
    }
}
