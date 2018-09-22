package com.zimokaka.uc.uac.user.service.impl;

import com.zimokaka.uc.uac.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

}
