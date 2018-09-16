package com.manage.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.common.Constants;
import com.manage.common.UrlConstants;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.system.bean.UserBean;
import com.manage.system.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by luya on 2018/6/8.
 */
@RestController
@RequestMapping(value = UrlConstants.URL_USER_MODEL)
public class UserController extends BaseController {

    Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 根据条件获取用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param group
     * @return
     */
    @GetMapping("/list")
    public APIResponse userList(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                @RequestParam(value = "account", required = false) String account,
                                @RequestParam(value = "userName", required = false) String userName,
                                @RequestParam(value = "group", required = false) String group) {

        pageNum = pageNum == null ? Constants.PAGEHELPER_PAGE_CURRENT : pageNum;
        pageSize = pageSize == null ? Constants.PAGEHELPER_PAGE_SIZE : pageSize;

        List<UserBean> userList = null;
        try {
            Page page = PageHelper.startPage(pageNum, pageSize, true);
            userList = userService.selectUserList(account, userName, Constants.STATUE_INVALID);
            Map result = new HashedMap();
            result.put("data", userList);
            result.put("pageNum", pageNum);
            result.put("total", page.getTotal());
            return APIResponse.toOkResponse(result);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_SELETE_ERROR);
        }
    }

    /**
     * 根据主键获取用户
     * id 为－１表示查询自己数据
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public APIResponse getUserByKey(@PathVariable("userId") String userId) {

        try {
            if(StringUtils.isEmpty(userId)){
                userId = getAccount();
            }
            UserBean userBean = userService.selectByPrimaryKey(userId);
            return APIResponse.toOkResponse(userBean);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_SELETE_ERROR);
        }
    }

    /**
     * 添加用户
     *
     * @param account
     * @param name
     * @param password
     * @param curPass
     * @param sex
     * @param phone
     * @param age
     * @param email
     * @return
     */
    @PostMapping("/add")
    public APIResponse addUser(@RequestParam("account") String account,
                               @RequestParam("name") String name,
                               @RequestParam("password") String password,
                               @RequestParam("curPass") String curPass,
                               @RequestParam("sex") String sex,
                               @RequestParam("phone") String phone,
                               @RequestParam("age") int age,
                               @RequestParam("email") String email,
                               @RequestParam("note") String note) {

        if (StringUtils.isEmpty(password) || !password.equals(curPass)) {
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_PWD_DIFF_ERROR);
        }
        UserBean userBean = new UserBean();
        userBean.setUserId(account);
        userBean.setUserName(name);
        userBean.setPassword(password);
        userBean.setSex(sex);
        userBean.setPhone(phone);
        userBean.setEmail(email);
        userBean.setNote(note);
        userBean.setUpdateUser(getUserName());
        try {
            userService.insertUser(userBean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_ADD_ERROR);
        }
        return APIResponse.toOkResponse();
    }


    /**
     * 修改用户
     *
     * @param account
     * @param name
     * @param sex
     * @param phone
     * @param age
     * @param email
     * @return
     */
    @PostMapping("/update")
    public APIResponse updateUser(@RequestParam(value = "account") String account,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "sex") String sex,
                                  @RequestParam(value = "phone") String phone,
                                  @RequestParam(value = "age") int age,
                                  @RequestParam(value = "email") String email,
                                  @RequestParam(value = "note") String note) {

        UserBean userBean = new UserBean();
        userBean.setUserId(account);
        userBean.setUserName(name);
        userBean.setSex(sex);
        userBean.setPhone(phone);
        userBean.setEmail(email);
        userBean.setNote(note);
        try {
            userService.updateUser(userBean);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_UPDATE_ERROR);
        }
        return APIResponse.toOkResponse();
    }


    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @PostMapping("/delete/{userId}")
    public APIResponse deleteUser(@PathVariable(value = "userId") String userId) {

        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_DELETE_ERROR);
        }
        return APIResponse.toOkResponse();
    }

    /**
     * userId 不为空修改该id的密码。为空则修改自己密码
     *
     * @param userId
     * @param oldPass
     * @param newPass
     * @param confPass
     * @return
     */
    @PostMapping("/password")
    public APIResponse changePassword(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam("oldPass") String oldPass,
            @RequestParam("newPass") String newPass,
            @RequestParam("confPass") String confPass) {

        try {
            // 两次密码一致
            if (!newPass.equals(confPass)) {
                return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_OLD_NEW_PWD_ERROR);
            }
            // 传入userId是修改其他人密码，不传入则是修改自己密码.并判断是否有权限修改
            if (!StringUtils.isEmpty(userId) && !getRoles().contains(Constants.SYSTEM_TYPE) && !getRoles().contains(Constants.ADMIN_TYPE)) {
                // 无权限修改他人密码
                return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_HAS_NO_ROLE_ERROR);
            }
            if (!StringUtils.isEmpty(userId) && (getRoles().contains(Constants.SYSTEM_TYPE) || getRoles().contains(Constants.ADMIN_TYPE))) {
                // 有权限修改他人密码
                if (userService.changePassword(userId, oldPass, newPass, false)) {
                    return APIResponse.toOkResponse();
                }
                return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_HAS_NO_ROLE_ERROR);
            } else if (StringUtils.isEmpty(userId)) {
                userId = getAccount();
                if (userService.changePassword(userId, oldPass, newPass, true)) {
                    return APIResponse.toOkResponse();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

        return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_CHG_PWD_ERROR);

    }
}
