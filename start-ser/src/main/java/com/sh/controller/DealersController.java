package com.sh.controller;

import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.DealersApi;
import com.sh.ctrl.entity.Dealers;
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
@Api(tags = "经销商控制器")
@RequestMapping("/dealers")
public class DealersController {

    private DealersApi dealersApi;

    @Autowired
    public void setDealersApi(DealersApi dealersApi) {
        this.dealersApi = dealersApi;
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
        return this.dealersApi.findAllByPage(page, size);
    }

    @ApiOperation(value = "saveDealers.添加或修改经销商")
    @PostMapping("saveDealers")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "经销商", dataType = "Object", name = "dealers")
            }
    )
    public ResultObWrapper saveDealers(@RequestBody Dealers dealers) {
        return this.dealersApi.saveDealers(dealers);
    }

    @ApiOperation(value = "deleteDealers.删除经销商")
    @PostMapping("deleteDealers")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "ID", dataType = "String", name = "id")
            }
    )
    public ResultObWrapper deleteDealers(String id) {
        return this.dealersApi.deleteDealers(id);
    }
}
