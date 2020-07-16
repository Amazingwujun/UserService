package com.lineyou.UserService.exception;

/**
 * Bean操作相关异常
 *
 * @author wujun
 * @date 2018/7/24 13:50
 */
public class BeanException extends GlobalException {
    public BeanException() {
        super();
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }
}
