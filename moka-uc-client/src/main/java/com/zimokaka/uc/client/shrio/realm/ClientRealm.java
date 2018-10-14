package com.zimokaka.uc.client.shrio.realm;

import com.zimokaka.uc.redis.util.RedisUtil;
import com.zimokaka.uc.shrio.constant.RedisConstant;
import com.zimokaka.uc.uac.user.po.UcUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ClientRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("***************************执行Shiro权限认证******************************");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String)super.getAvailablePrincipal(principalCollection);
        //到数据库查是否有此对象
        RedisUtil redisUtil = RedisUtil.getInstance();
        UcUser user = (UcUser) redisUtil.get(RedisConstant.REDIS_USER+loginName);

        if(user!=null){
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
            /*//获取角色
            Set<CommentCodeVO> roles=uacUtil.getRoleMapByUid(user.get("uid"));

            if(roles!=null){
                Set<String> roleSet=new HashSet<String>();
                List<String> roleIds=new ArrayList<String>();
                for(CommentCodeVO vo:roles){
                    roleSet.add(vo.getCode());
                    roleIds.add(vo.getId());
                }
                //添加角色
                info.setRoles(roleSet);

                Set<String> permission=uacUtil.getPermissionsMapByRoleId(roleIds);
                if(permission!=null){
                    info.addStringPermissions(permission);
                }
            }*/
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("***************************执行Shiro登录认证******************************");
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;

        logger.info("验证当前Subject时获取到token为："+token);

        //查出是否有此用户
        RedisUtil redisUtil = RedisUtil.getInstance();
        UcUser user = (UcUser) redisUtil.get(RedisConstant.REDIS_USER+token.getUsername());
        if(user!=null){
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        }
        return null;
    }
}
