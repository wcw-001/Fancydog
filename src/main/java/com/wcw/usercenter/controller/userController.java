package com.wcw.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wcw.usercenter.common.BaseResponse;
import com.wcw.usercenter.common.ErrorCode;
import com.wcw.usercenter.common.ResultUtils;
import com.wcw.usercenter.exception.BusinessException;
import com.wcw.usercenter.exception.ThrowUtils;
import com.wcw.usercenter.model.domain.User;
import com.wcw.usercenter.model.domain.request.*;
import com.wcw.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
/**
 * 用户接口
 * @author wcw
 *
 */

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wcw.usercenter.contant.UserConstant.ADMIN_RILE;
import static com.wcw.usercenter.contant.UserConstant.USER_LOGIN_STATE;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;
import static org.apache.commons.lang3.Streams.stream;

@RestController
@RequestMapping("/user")
public class userController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userCode = userRegisterRequest.getUserCode();
        long id = userService.userRegister(userAccount, userPassword, checkPassword, userCode);
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userCode)) {
           // return ResultUtils.error(ErrorCode.NULL_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword, userCode);
        return ResultUtils.success(result);


    }

    /**
     * 用户登入
     * @param userLoginRequest
     * @param request
     * @return
     */

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        User user = userService.doLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     *
     * @param request
     * @return
     */

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
       Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
       User currentUser = (User) userObj;
       if (currentUser == null){
           return  null;
       }
       long userId = currentUser.getId();

       User user = userService.getById(userId);
       User safetyUser = userService.getSafetyUser(user);
       return ResultUtils.success(safetyUser);
    }
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request) {
        if(!isAdmin(request)){
            return new BaseResponse(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList=userService.list(queryWrapper);
        List<User> user= userList.stream().map(userService::getSafetyUser).collect(Collectors.toList());
        return ResultUtils.success(user);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody UserDeleteRequest deleteRequest, HttpServletRequest request) {
        if(!isAdmin(request)){
            return null;
        }
        if (deleteRequest==null||deleteRequest.getId() <= 0) {
            return null;
        }else{
        Boolean result = userService.removeById(deleteRequest.getId());
        return  ResultUtils.success(result);

        }
    }
    @PostMapping("/update/my")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,
                                              HttpServletRequest request) {
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     *用户自己更新自己的项目
     * @param updatePasswordRequest
     * @param request
     * @return
     */
    @PostMapping("/update/password")
    public BaseResponse<Boolean> updateUserPassword(@RequestBody UserUpdatePasswordRequest updatePasswordRequest,
                                                    HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }
        boolean updateUserPassword = userService.updateUserPassword(updatePasswordRequest, request);
        if (updateUserPassword) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
    }


    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_RILE;
    }

}
