package com.sh.controller;

import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.UserApi;
import com.sh.ctrl.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户控制器")
@RequestMapping("/user")
public class UserController {

    private UserApi userApi;

    @Autowired
    public void setUserApi(UserApi userApi) {
        this.userApi = userApi;
    }

    @ApiOperation(value = "findAll.查询所有")
    @PostMapping("findAll")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "页码", dataType = "String", name = "page"),
                    @ApiImplicitParam(value = "数量", dataType = "String", name = "size")
            }
    )
    public ResultObListWrapper findAll(Integer page, Integer size) {
        return this.userApi.findAllByPage(page, size);
    }

    @ApiOperation(value = "saveUser.添加或修改用户")
    @PostMapping("saveUser")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "用户", dataType = "Object", name = "user")
            }
    )
    public ResultObWrapper saveUser(@RequestBody User user) {
        return this.userApi.saveUser(user);
    }

    @ApiOperation(value = "deleteUser.删除用户")
    @PostMapping("deleteUser")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "ID", dataType = "String", name = "id")
            }
    )
    public ResultObWrapper deleteUser(String id) {
        return this.userApi.deleteUser(id);
    }
}
