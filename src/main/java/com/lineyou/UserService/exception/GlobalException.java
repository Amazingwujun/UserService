package com.lineyou.UserService.exception;

/**
 * 项目顶级异常
 *
 * @author Jun
 * @date 2019-01-12 16:26
 */
public class GlobalException extends RuntimeException {
    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    protected GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
