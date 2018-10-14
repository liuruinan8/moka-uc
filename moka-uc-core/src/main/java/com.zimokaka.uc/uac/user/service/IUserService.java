package com.zimokaka.uc.uac.user.service;

import com.zimokaka.uc.uac.user.po.UcUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public Page<UcUser> findAll(int pageNo, int pageSize, Sort.Direction dir, String str);

    UcUser findByUsername(String uid);

    void saveUser(UcUser user);
}
