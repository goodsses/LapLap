package com.sh.controller;

import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.SysUserApi;
import com.sh.ctrl.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/9 17:39
 */
@RestController
@RequestMapping("beijing/login")
@Api(tags = "login.登录")
public class LoginController {


    private SysUserApi sysUserApi;

    @Autowired
    public void setSysUserApi(SysUserApi sysUserApi) {
        this.sysUserApi = sysUserApi;
    }

    @GetMapping("login")
    @ApiOperation(value = "login")
    public ResultObWrapper<SysUser> getLogin() {
        return sysUserApi.findAll();
    }

}

