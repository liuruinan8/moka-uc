package com.zimokaka.uc.shrio.config;

import java.util.HashMap;
import java.util.Map;

import com.zimokaka.uc.shrio.cache.redis.RedisCacheManager;
import com.zimokaka.uc.shrio.conf.ShiroConf;
import com.zimokaka.uc.shrio.realm.MyShiroRealm;
import com.zimokaka.uc.shrio.session.dao.RedisSessionDAO;
import com.zimokaka.uc.shrio.session.mgt.TokenSessionManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;


/**
 * shiro配置
 * @author ming
 *
 */
@Configuration
@Order(2)
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /*@Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }  */
    /**
     * st-shiro配置
     * @return
     */
    @ConfigurationProperties(prefix="zimokaka.shiro.conf")
    @Bean("shiroConf")
    @Primary
    public ShiroConf getSTShiroConf(){
        return new ShiroConf();
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm=new MyShiroRealm();
        //myShiroRealm.setCacheManager(getEhCacheManager());
        return myShiroRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(){
        return new RedisSessionDAO();
    }

    @Bean
    public SessionManager sessionManager() {
        if(getSTShiroConf().isOpenToken()){
            DefaultSessionManager sessionManager = new TokenSessionManager();
            sessionManager.setSessionDAO(redisSessionDAO());
            sessionManager.setGlobalSessionTimeout(getSTShiroConf().getSessionTimeout());
            sessionManager.setCacheManager(redisCacheManager());
            sessionManager.setDeleteInvalidSessions(true);//删除过期的session
            //sessionManager.setSessionIdCookieEnabled(true);
            //sessionManager.setSessionIdCookie(sessionIdCookie());
            return sessionManager;
        }else{
            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setSessionDAO(redisSessionDAO());
            sessionManager.setGlobalSessionTimeout(getSTShiroConf().getSessionTimeout());
            sessionManager.setCacheManager(redisCacheManager());
            sessionManager.setDeleteInvalidSessions(true);//删除过期的session
            sessionManager.setSessionIdCookieEnabled(true);
            sessionManager.setSessionIdCookie(sessionIdCookie());
            return sessionManager;
        }
    }

    //设置cookie
    @Bean
    public Cookie sessionIdCookie(){
        Cookie sessionIdCookie=new SimpleCookie("STID");
        sessionIdCookie.setMaxAge(-1);
        sessionIdCookie.setHttpOnly(true);
        return sessionIdCookie;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(myShiroRealm());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        //设置cache
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl(getSTShiroConf().getLoginView());
        shiroFilterFactoryBean.setSuccessUrl(getSTShiroConf().getSuccessUrl());
        //不拦截的路径
        //Assert.notNull(this.getSTShiroConf().getSysanon());
        if(this.getSTShiroConf().getSysanon()!=null){
            for(String str:this.getSTShiroConf().getSysanon()){
                filterChainDefinitionMap.put(str, "anon");
                logger.debug("shiro:["+str+"："+"anon"+"]");
            }
        }

        filterChainDefinitionMap.put("/**", "authc");
        //filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }/**/


}
