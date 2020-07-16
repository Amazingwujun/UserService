package com.lineyou.UserService.entity;


import com.lineyou.UserService.constant.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应封装对象
 *
 * @author Jun
 * @date 2018-11-16 12:36
 */
@Data
public class Response<T> implements Serializable {
    //@formatter:off

    /** 响应编码 */
    int code;

    /** 响应数据 */
    T data;

    /** 响应数据 */
    String msg;
    // @formatter:on

    public Response() {
    }

    private Response(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Response<T> success() {
        return new Response<>(ResponseCode.MISSION_SUCCESS.getCode(), null, ResponseCode.MISSION_SUCCESS.getValue());
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(ResponseCode.MISSION_SUCCESS.getCode(), data, null);
    }

    public static <T> Response<T> failure(int code, String msg) {
        return new Response<>(code, null, msg);
    }

    public static <T> Response<T> failure(ResponseCode responseCode) {
        return new Response<>(responseCode.getCode(), null, responseCode.getValue());
    }

    public static <T> Response<T> error(ResponseCode responseCode) {
        return new Response<>(responseCode.getCode(), null, responseCode.getValue());
    }
}
