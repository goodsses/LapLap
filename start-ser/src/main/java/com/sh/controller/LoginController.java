package com.sh.controller;

import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.SysUserApi;
import com.sh.ctrl.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/9 17:39
 */
@RestController
@RequestMapping("/login")
@Api(tags = "login.登录")
public class LoginController {


    private SysUserApi sysUserApi;

    @Autowired
    public void setSysUserApi(SysUserApi sysUserApi) {
        this.sysUserApi = sysUserApi;
    }

    @ApiOperation(value = "login.登录")
    @PostMapping("login")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "账号", dataType = "String", name = "userName"),
                    @ApiImplicitParam(value = "密码", dataType = "String", name = "password")
            }
    )
    public ResultObWrapper<SysUser> getLogin(@RequestParam String userName, @RequestParam String password) {
        return sysUserApi.getLogin(userName, password);
    }

}

