package com.zimokaka.uc.shrio.conf;

import java.util.List;


public class ShiroConf {
    private String  cookiePath;
    private String  successUrl;
    private String  loginView;
    private boolean  openToken;
    private long  sessionTimeout;
    private String  algorithmName;
    private String  hashIterations;
    private List<String> sysanon;
    private List<String>  allowedOrigins;
    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    public boolean isOpenToken() {
        return openToken;
    }

    public void setOpenToken(boolean openToken) {
        this.openToken = openToken;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(String hashIterations) {
        this.hashIterations = hashIterations;
    }

    public List<String> getSysanon() {
        return sysanon;
    }

    public void setSysanon(List<String>  sysanon) {
        this.sysanon = sysanon;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
