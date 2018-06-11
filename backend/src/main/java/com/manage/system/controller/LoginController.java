package com.manage.system.controller;

import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.system.bean.UserBean;
import com.manage.system.service.UserService;
import com.manage.util.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController {

    private Log logger = LogFactory.getLog(LoginController.class);

    public static final String LOGIN_HTML = "main-back.html";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

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
            @RequestParam("account") String account,
            @RequestParam("password") String password) {

        final String randomKey = jwtTokenUtil.getRandomKey();
        //获取用户和对应的角色
        UserBean userBean = null;
        try {
            userBean = userService.selectUserAndRoleByAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.AUTH_REQUEST_ERROR);
        }

        if (userBean != null && BCrypt.checkpw(password, userBean.getPassword())) {
            final String token = jwtTokenUtil.generateToken(userBean, randomKey);
            return APIResponse.toOkResponse(token);
        }
        return APIResponse.toExceptionResponse(BizExceptionStatusEnum.AUTH_REQUEST_ERROR);
    }

}
