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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                    @ApiImplicitParam(value = "名称", dataType = "String", name = "name"),
                    @ApiImplicitParam(value = "手机号", dataType = "String", name = "mobile"),
                    @ApiImplicitParam(value = "页码", dataType = "String", name = "page"),
                    @ApiImplicitParam(value = "数量", dataType = "String", name = "size")
            }
    )
    public ResultObListWrapper findAll(String name, String mobile, Integer page, Integer size) {
        return this.userApi.findAllByPage(name, mobile, page, size);
    }

    @ApiOperation(value = "saveUser.添加或修改用户")
    @PostMapping("saveUser")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "用户", dataType = "Object", name = "user")
            }
    )
    public ResultObWrapper saveUser(@ModelAttribute User user) {
        return this.userApi.saveUser(user);
    }

    @ApiOperation(value = "deleteUser.删除用户")
    @PostMapping("deleteUser")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "ID", dataType = "String", name = "ids")
            }
    )
    public ResultObWrapper deleteUser(String ids) {
        return this.userApi.deleteUser(ids);
    }

    @ApiOperation(value = "exportInfo.导出信息")
    @RequestMapping(value = "exportInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<byte[]> exportInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "userInfo.xls");// 文件的属性，也就是文件叫什么吧
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);// 内容是字节流
        return new ResponseEntity<>(this.userApi.exportInfo(), headers, HttpStatus.OK);
    }
}
