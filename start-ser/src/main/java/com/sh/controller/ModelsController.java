package com.sh.controller;

import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.ModelsApi;
import com.sh.ctrl.entity.Models;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "车型控制器")
@RequestMapping("/models")
public class ModelsController {

    private ModelsApi modelsApi;

    @Autowired
    public void setModelsApi(ModelsApi modelsApi) {
        this.modelsApi = modelsApi;
    }

    @ApiOperation(value = "findAll.查询所有")
    @PostMapping("findAll")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "类型", dataType = "String", name = "type"),
                    @ApiImplicitParam(value = "页码", dataType = "String", name = "page"),
                    @ApiImplicitParam(value = "数量", dataType = "String", name = "size")
            }
    )
    public ResultObListWrapper findAll(String type, Integer page, Integer size) {
        return this.modelsApi.findAllByPage(type, page, size);
    }

    @ApiOperation(value = "saveModels.添加或修改试题")
    @PostMapping("saveModels")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "试题", dataType = "Object", name = "questionBank")
            }
    )
    public ResultObWrapper saveModels(@ModelAttribute Models models) {
        return this.modelsApi.saveModels(models);
    }

    @ApiOperation(value = "deleteModels.删除试题")
    @PostMapping("deleteModels")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "ID", dataType = "String", name = "ids")
            }
    )
    public ResultObWrapper deleteModels(String ids) {
        return this.modelsApi.deleteModels(ids);
    }
}
