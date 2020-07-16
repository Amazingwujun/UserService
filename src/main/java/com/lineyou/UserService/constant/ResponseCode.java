package com.lineyou.UserService.constant;

/**
 * 返回响应,下表为新E智通用响应代码
 *  <table BORDER CELLPADDING=3 CELLSPACING=1>
 *     <tr>
 *         <th ALIGN=CENTER>code</th>
 *         <th ALIGN=CENTER>value</th>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER>200</td>
 *         <td ALIGN=CENTER>操作成功</td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER>400</td>
 *         <td ALIGN=CENTER>令牌错误或失效</td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER>404</td>
 *         <td ALIGN=CENTER>找不到对应的资源</td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER>500</td>
 *         <td ALIGN=CENTER>服务忙</td>
 *     </tr>
 *     <tr>
 *         <td ALIGN=CENTER>520</td>
 *         <td ALIGN=CENTER>服务熔断</td>
 *     </tr>
 * </table>
 *
 * @author wujun
 * @date 2018/4/20 11:09
 */
public enum ResponseCode {

    MISSION_SUCCESS(200, "操作成功"),

    TOKEN_EXPIRE_ERR(400, "用户已存在"),
    REQUEST_ARGS_MISSING(401, "参数不全"),
    HTTP_METHOD_ERR(402, "请求方法错误"),
    DUPLICATE_DATA_ERR(403, "关键数据（手机、身份证、邮箱等）出现重复"),
    INFO_NOT_FOUND_ERR(404, "找不到您要的信息"),
    LOGIN_ERR(405, "用户名或密码错误"),
    SERVICE_BUSY_ERR(406, "服务繁忙，请稍后再试"),
    ILLEGAL_ARGS_ERR(407, "参数错误"),
    CHECK_CODE_ERR(408, "短信校验码失效或错误"),
    MISSION_FAILED_ERR(409, "操作失败"),
    VEHICLE_ALREADY_BOUND(410, "车辆已被绑定"),
    PERMISSION_ERR(411, "您缺乏操作权限"),
    OVER_RETRY_TIME_ERR(412, "密码错误次数超过五次,请五分钟后再尝试登入"),
    SMS_REQUIRE_TOO_FREQUENT(413, "短信请求过于频繁，请稍后再试"),

    /**
     * 当App收到此 code 时，表明当前客户登入状态时被其他客户挤下来的，需要弹窗提醒
     */
    TOKEN_NOT_MATCH_ERR(415, "令牌错误"),


    SERVER_ERR(500, "服务忙"),
    CIRCUIT_OPEN(520, "服务熔断");

    private int code;

    private String value;

    ResponseCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
