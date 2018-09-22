package com.zimokaka.uc.uac.util;

import java.util.List;
import java.util.Map;

/**
 * 整合用户权限角色信息 统一对外提供服务
 */
public class UACUtil {

    private static UACUtil uacUtil = null;
    public static UACUtil getInstance(){
        if(uacUtil == null){
            uacUtil = new UACUtil();
        }
       return uacUtil;
    }
    public Map getUserMapByUid(String uid){
        return null;
    }

    public List<Map> getRoleMapByUid(String uid){
        return null;
    }

    public List<Map> getPermissionsMapByRoleId(String roleId){
        return null;
    }
}
