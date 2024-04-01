package models;

public class BusinessException extends Exception {

    private final ErrorCode errorCode;

    public BusinessException(String message) {
        super(message);
        this.errorCode = ErrorCode.GENERAL;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.GENERAL;
    }


    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public BusinessException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public enum ErrorCode {
        GENERAL,
        USER_NOT_FOUND,
        INVALID_INPUT,
    }
}
