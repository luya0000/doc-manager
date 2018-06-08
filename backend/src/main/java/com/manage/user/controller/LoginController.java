package com.manage.user.controller;

import com.manage.common.APIResponse;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.user.bean.UserBean;
import com.manage.user.model.UserDto;
import com.manage.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String LOGIN_HTML = "index.html";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //@Autowired
    //private UserService userService;

    @RequestMapping("/")
    public String index() {
        return LOGIN_HTML;
    }

    @RequestMapping(value = "index")
    public String example(HttpServletRequest request) {
        return LOGIN_HTML;
    }

    @PostMapping(value = "logout")
    public ModelAndView signOut(HttpServletRequest request) {
        return new ModelAndView(LOGIN_HTML);
    }


    @PostMapping(value = "${jwt.auth-path}")
    @ResponseBody
    public APIResponse createAuthenticationToken(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        final String randomKey = jwtTokenUtil.getRandomKey();
        //获取用户和对应的角色
        UserDto user = new UserDto();//userService.selectUserAndRoleByAccount(authRequest.getUserName());
        user.setId(1);
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(user, userBean);
        final String token = jwtTokenUtil.generateToken(userBean, randomKey);
        return APIResponse.toOkResponse(token);
    }

    @PostMapping("/sys/user/password")
    public APIResponse changePassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("confPassword") String confPassword) {
        //String userId = getUserId();

      /*  if (userService.changePassword(Integer.parseInt(userId),
                userChgPwdRequest.getOriginPwd(),
                userChgPwdRequest.getPassword())) {
            return APIResponse.toOkResponse();
        }*/

        return APIResponse.toExceptionResponse(BizExceptionStatusEnum.USER_CHG_PWD_ERROR);

    }
}
