package com.wcw.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author wcw
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -4356425365213683936L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String userCode;

}
