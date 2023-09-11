package com.wcw.usercenter.exception;

import com.wcw.usercenter.common.ErrorCode;

/**
 * 自定义异常类
 * @author wcw
 */
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;
    public  BusinessException(String meaaage,int code,String description){
        super(meaaage);
        this.code = code;
        this.description =description;
    }
    public  BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description =errorCode.getDescription();
    }
    public  BusinessException(ErrorCode errorCode,String description){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description =description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
