package com.zimokaka.uc.shrio.remote.impl;

import com.zimokaka.uc.shrio.remote.RemoteServiceInterface;
import com.zimokaka.uc.shrio.session.dao.RedisSessionDAO;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class RemoteServiceRedisImpl implements RemoteServiceInterface {

    //@Autowired
   // private AuthorizationService authorizationService;
    @Autowired
    private RedisSessionDAO sessionDAO;
    public Session getSession(String appKey, Serializable sessionId) {
        return sessionDAO.readSession(sessionId);
    }
    public Serializable createSession(Session session) {
        return sessionDAO.create(session);
    }
    public void updateSession(String appKey, Session session) {
        sessionDAO.update(session);
    }
    public void deleteSession(String appKey, Session session) {
        sessionDAO.delete(session);
    }
}
