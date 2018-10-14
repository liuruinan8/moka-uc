package com.zimokaka.uc.uac.user.service.impl;

import com.zimokaka.uc.uac.user.po.UcUser;
import com.zimokaka.uc.uac.user.repository.UserRepository;
import com.zimokaka.uc.uac.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    @Qualifier("ucUserRepository")
    private UserRepository userRepository;

    @Override
    @Transactional
    public Page<UcUser> findAll(int pageNo, int pageSize, Sort.Direction dir, String str){
        PageRequest request = buildPageRequest(pageNo, pageSize, dir, str);
        Page<UcUser> users = userRepository.findAll(request);
        return users;
    }

    public void insert() {
        UcUser user = new UcUser();
        //user.setName("管理员");
       // user.setUid("1");
        //UcUser result = this.userRepository.save(user);
       // System.out.println(result);
    }


    @Override
    public UcUser findByUsername(String uid) {
        return this.userRepository.findByUsername(uid);
    }

    @Override
    public void saveUser(UcUser user) {
        userRepository.save(user);
    }

    /**
     * 构建PageRequest对象
     * @param num
     * @param size
     * @param asc
     * @param string
     * @return
     */
    private PageRequest buildPageRequest(int num, int size, Sort.Direction asc,
                                         String string) {
        return new PageRequest(num-1, size,null,string);
    }
}
