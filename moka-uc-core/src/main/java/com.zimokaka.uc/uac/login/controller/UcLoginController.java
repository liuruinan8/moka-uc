package com.zimokaka.uc.uac.login.controller;


import com.zimokaka.uc.shrio.util.CipherUtil;
import com.zimokaka.uc.uac.user.po.UcUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UcLoginController {
    private static final Logger logger = LoggerFactory.getLogger(UcLoginController.class);

    //初期化
    @RequestMapping(value = "/login")
    public String login(Model model) {
        UcUser ucUser = new UcUser();
        model.addAttribute("ucUser",ucUser);
        //model.addAttribute("loginError","");
        return "login";
    }

    //登录
    @RequestMapping(value = "/login",params="action=login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username") String userName,
                        @RequestParam(value = "password") String userPsw,Model model,RedirectAttributes redirectAttributes ) {
        UcUser ucUser = new UcUser();

        String password = CipherUtil.generatePassword(userPsw);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            System.out.println("----------------------------");
            if (!currentUser.isAuthenticated()){//使用shiro来验证
                token.setRememberMe(true);
                currentUser.login(token);//验证角色和权限
            }
            redirectAttributes.addFlashAttribute("userName",userName);
            return "redirect:index";
        } catch (UnknownAccountException e) {
            logger.error(e.getMessage());
            model.addAttribute("loginError",true);
            model.addAttribute("ucUser",ucUser);
            return "login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("loginError",true);
            model.addAttribute("ucUser",ucUser);
            return "login";
        }catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("loginError",true);
            model.addAttribute("ucUser",ucUser);
            return "login";
        }
    }
    @RequestMapping("/index")
    public String welcomeIndex(@ModelAttribute("userName") String userName, Model model) {
        model.addAttribute("userName",userName);
        return "index";
    }
}
