package com.zimokaka.uc.uac.user.service.impl;

import com.zimokaka.uc.uac.user.po.UcUser;
import com.zimokaka.uc.uac.user.repository.UserRepository;
import com.zimokaka.uc.uac.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    @Qualifier("ucUserRepository")
    private UserRepository userRepository;

    public void insert() {
        UcUser user = new UcUser();
        //user.setName("管理员");
       // user.setUid("1");
        //UcUser result = this.userRepository.save(user);
       // System.out.println(result);
    }


    @Override
    public List<UcUser> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UcUser getUserMapByUid(String uid) {
        return this.userRepository.findOne(uid);
    }
}
