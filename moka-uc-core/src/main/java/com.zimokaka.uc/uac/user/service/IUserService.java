package com.zimokaka.uc.uac.user.service;

import com.zimokaka.uc.uac.user.po.UcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public List<UcUser> findAll();

    UcUser findByUsername(String uid);
}
