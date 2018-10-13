package com.zimokaka.uc.uac.user.controller;

import com.zimokaka.uc.uac.user.po.UcUser;
import com.zimokaka.uc.uac.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userServiceImpl;

    @RequestMapping("/findAll")
    public List<UcUser> findAll() {
        System.out.println("查询所有用户");
        List<UcUser> userList = userServiceImpl.findAll();
        for (UcUser user : userList) {
            System.out.println("用户:" + user);
        }
        return userList;
    }


}
