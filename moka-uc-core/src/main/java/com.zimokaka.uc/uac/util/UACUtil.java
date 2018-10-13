package com.zimokaka.uc.uac.util;

import com.zimokaka.uc.uac.user.po.UcUser;
import com.zimokaka.uc.uac.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整合用户权限角色信息 统一对外提供服务
 */
@Component
public class UACUtil {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userServiceImpl;

    private static UACUtil uacUtil;

    // 非Controller 注入方式
    @PostConstruct
    public void init() {
        uacUtil = this;
        uacUtil.userServiceImpl = this.userServiceImpl;
    }
    public static UACUtil getInstance(){
        if(uacUtil == null){
            uacUtil = new UACUtil();
        }
       return uacUtil;
    }

    public Map getUserMapByUid(String uid){

        UcUser ucUser = userServiceImpl.getUserMapByUid(uid);
        Map map = new HashMap();
        if(ucUser!=null){
            map.put("uid",ucUser.getUid());
            map.put("password",ucUser.getPassword());
            map.put("salt",ucUser.getSalt());
        }
        return map;
    }

    public List<Map> getRoleMapByUid(String uid){
        return null;
    }

    public List<Map> getPermissionsMapByRoleId(String roleId){
        return null;
    }
}
