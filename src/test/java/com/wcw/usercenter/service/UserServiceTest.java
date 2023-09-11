package com.wcw.usercenter.service;

import com.wcw.usercenter.model.domain.User;
import com.wcw.usercenter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户测试
 *
 * @author wcw
 *
 */
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;
   /*  @Test
   public void testAddUser(){
        User user = new User();
        user.setUsername("wcw");
        user.setUserAccount("18779750601");
        user.setAvatarUrl("https://profile-avatar.csdnimg.cn/2cff2284aae4478cafdb6cb23ffdfbb4_qq_59400558.jpg!1");
        user.setGender(0);
        user.setUserPassword("12345678");
        user.setPhone("888888888");
        user.setEmail("66666666@qq.com");
        user.setUserCode("001");
        boolean result = userService.save(user);
        System.out.println("新增用户ID："+user.getId());
        // 断言，判断一下是否符合预期结果。assertTrue：是否保存成功
        Assertions.assertTrue(result);
    }*/

    @Test
    void userRegister() {
        String userAccount = "dogwcw";
        String userPassword = "";
        String checkPassWord = "12345678";
        String userCode="1234";
        long result = userService.userRegister(userAccount, userPassword,checkPassWord,userCode);
        Assertions.assertEquals(-1,result);
        userAccount="wc";
        result = userService.userRegister(userAccount, userPassword,checkPassWord,userCode);
        Assertions.assertEquals(-1,result);
        userAccount="wcwc";
        userPassword="123456";
        result = userService.userRegister(userAccount, userPassword,checkPassWord,userCode);
        Assertions.assertEquals(-1,result);
        userAccount="wc wc";
        userPassword="12345678";
        result = userService.userRegister(userAccount, userPassword,checkPassWord,userCode);
        Assertions.assertEquals(-1,result);
        userAccount="18779750601";
        userPassword="123456789";
        result = userService.userRegister(userAccount, userPassword,checkPassWord,userCode);
        Assertions.assertEquals(-1,result);
        userAccount="dogwcw";
        result = userService.userRegister(userAccount, userPassword,checkPassWord,userCode);
        Assertions.assertEquals(-1,result);


    }
}
