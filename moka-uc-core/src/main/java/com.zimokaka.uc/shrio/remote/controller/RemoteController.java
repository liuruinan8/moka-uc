package com.zimokaka.uc.shrio.remote.controller;

import com.zimokaka.uc.shrio.remote.RemoteServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider/shrio/remote")
public class RemoteController {

    @Autowired
    @Qualifier(value = "remoteServiceImpl")
    private RemoteServiceInterface remoteServiceRedisImpl;
}
