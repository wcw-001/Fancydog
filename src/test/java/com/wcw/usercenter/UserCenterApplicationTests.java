package com.wcw.usercenter;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.wcw.usercenter.mapper.UserMapper;
import com.wcw.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {
    @Resource
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5  == userList.size() ,"");
        userList.forEach(System.out::println);
    }

}
