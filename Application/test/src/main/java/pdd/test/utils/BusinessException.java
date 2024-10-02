package pdd.test.utils;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
