package com.zimokaka.uc.shrio.remote.impl;

import com.zimokaka.uc.shrio.remote.RemoteServiceInterface;
import com.zimokaka.uc.shrio.session.dao.RedisSessionDAO;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("remoteServiceImpl")
public class RemoteServiceRedisImpl implements RemoteServiceInterface {

    //@Autowired
   // private AuthorizationService authorizationService;
    @Autowired
    private RedisSessionDAO sessionDAO;
    @Override
    public Session getSession(String appKey, Serializable sessionId) {
        return sessionDAO.readSession(sessionId);
    }
    @Override
    public Serializable createSession(Session session) {
        return sessionDAO.create(session);
    }

    @Override
    public void updateSession(String appKey, Session session) {
        sessionDAO.update(session);
    }
    @Override
    public void deleteSession(String appKey, Session session) {
        sessionDAO.delete(session);
    }
}
