package pdd.test.utils;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
