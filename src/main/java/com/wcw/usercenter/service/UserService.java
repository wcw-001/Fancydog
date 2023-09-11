package com.wcw.usercenter.service;

import com.wcw.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author wcw
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-08-02 13:11:42
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */

    long userRegister(String userAccount, String userPassword, String checkPassword,String userCode);

    /**
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @return 脱敏用户信息
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
    int userLogout(HttpServletRequest request);
}
