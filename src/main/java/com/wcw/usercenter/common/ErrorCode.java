package com.wcw.usercenter.common;


//import org.omg.CORBA.NO_PERMISSION;

/**
 * 错误码
 *
 * @author wcw
 */
public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40101,"未登入",""),
    NO_AUTH(40100,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常",""),
    OPERATION_ERROR(50001, "操作失败", "操作失败");
    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
