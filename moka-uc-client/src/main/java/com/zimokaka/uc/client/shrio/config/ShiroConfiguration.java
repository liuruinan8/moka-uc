package com.zimokaka.uc.client.shrio.config;

import com.zimokaka.uc.client.shrio.realm.ClientRealm;
import com.zimokaka.uc.client.shrio.session.ClientSessionDAO;
import com.zimokaka.uc.redis.serialize.RedisObjectSerializer;
import com.zimokaka.uc.shrio.cache.redis.RedisCacheManager;
import com.zimokaka.uc.shrio.conf.ShiroConf;
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
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * shiro配置
 * @author ming
 *
 */
@Configuration
@Order(2)
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
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
    public ClientRealm myShiroRealm(RedisCacheManager redisCacheManager) {
        ClientRealm myShiroRealm=new ClientRealm();
        myShiroRealm.setCacheManager(redisCacheManager);
        return myShiroRealm;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager=new RedisCacheManager();
        //redisCacheManager.setRedisTemplate(redisTemplate);
        return redisCacheManager;
    }

    @Bean
    public ClientSessionDAO redisSessionDAO(){
        redisTemplate.setValueSerializer(new RedisObjectSerializer());
        ClientSessionDAO sessionDAO=new ClientSessionDAO();
        sessionDAO.setRedisTemplate(redisTemplate);
        return sessionDAO;
    }

    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        /*if(getSTShiroConf().isOpenToken()){
            DefaultSessionManager sessionManager = new TokenSessionManager();
            sessionManager.setSessionDAO(redisSessionDAO());
            sessionManager.setGlobalSessionTimeout(getSTShiroConf().getSessionTimeout());
            sessionManager.setCacheManager(redisCacheManager());
            sessionManager.setDeleteInvalidSessions(true);//删除过期的session
            //sessionManager.setSessionIdCookieEnabled(true);
            //sessionManager.setSessionIdCookie(sessionIdCookie());
            return sessionManager;
        }else{*/
            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setSessionDAO(redisSessionDAO());
            sessionManager.setGlobalSessionTimeout(getSTShiroConf().getSessionTimeout());
            sessionManager.setCacheManager(redisCacheManager());
            sessionManager.setDeleteInvalidSessions(true);//删除过期的session
            sessionManager.setSessionIdCookieEnabled(true);
            sessionManager.setSessionIdCookie(sessionIdCookie());
            return sessionManager;
        /*}*/
    }

    //设置cookie
    @Bean(name = "sessionIdCookie")
    public Cookie sessionIdCookie(){
        Cookie sessionIdCookie=new SimpleCookie("STID");
        sessionIdCookie.setMaxAge(-1);
        sessionIdCookie.setHttpOnly(true);
        return sessionIdCookie;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(myShiroRealm(redisCacheManager()));
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

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl(getSTShiroConf().getLoginView());
        shiroFilterFactoryBean.setSuccessUrl(getSTShiroConf().getSuccessUrl());


        //设置认证过滤器
        Map<String, Filter>filters = new LinkedHashMap();
        filters.put("authc", getClientAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);


        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //不拦截的路径
        if(this.getSTShiroConf().getSysanon()!=null){
            for(String str:this.getSTShiroConf().getSysanon()){
                filterChainDefinitionMap.put(str, "anon");
                logger.debug("shiro:["+str+"："+"anon"+"]");
            }
        }
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    public ClientAuthenticationFilter  getClientAuthenticationFilter(){
        return new ClientAuthenticationFilter();
    }
}
