package com.wjxinfo.web;

import com.wjxinfo.core.auth.utils.cache.UserUtils;
import com.wjxinfo.core.base.web.servlet.CaptchaServlet;
import com.wjxinfo.core.base.web.utils.I18nUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping(value = "checkCaptcha")
    public String checkCaptcha(String captcha, HttpServletRequest request) {
        if (CaptchaServlet.validate(request, captcha)) {
            return "true";
        }
        return "false";
    }

    /**
     * User Login
     *
     * @return : view path
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        logger.info("User Login Start...");
        if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isAuthenticated()) {
            try {
                WebUtils.issueRedirect(request, response, "/");
            } catch (Exception e) {
                logger.error(e.getMessage());
                return "redirect:/";
            }
        }

        // BT 11184
        if (UserUtils.getUser().getId() != null) {
            logger.warn("User has logined, please logout first,  userId = " + UserUtils.getUser().getId());
            //return "redirect:/";

            if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isAuthenticated()) {
                return "home";
            }
        }


        I18nUtils.setDefaultLocal(request);

        return "idea16/auth/login";
    }

    /**
     * User Login
     *
     * @param userName : user name
     * @param model    : model
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, HttpServletRequest request, Model model) {
        logger.info("LoginController.login(...)");
        System.out.println("LoginController.login(...)");
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);

        return "idea16/auth/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "idea16/auth/login";
    }

    @ResponseBody
    @RequestMapping("/keep-alive")
    public String keepAlive() {
        return "";
    }
}
