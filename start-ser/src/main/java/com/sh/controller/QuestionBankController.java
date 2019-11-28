package com.sh.controller;

import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.QuestionBankApi;
import com.sh.ctrl.entity.QuestionBank;
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
@Api(tags = "问卷控制器")
@RequestMapping("/questionBank")
public class QuestionBankController {

    private QuestionBankApi questionBankApi;

    @Autowired
    public void setQuestionBankApi(QuestionBankApi questionBankApi) {
        this.questionBankApi = questionBankApi;
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
        return this.questionBankApi.findAllByPage(page, size);
    }

    @ApiOperation(value = "saveQuestion.添加或修改试题")
    @PostMapping("saveQuestion")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "试题", dataType = "Object", name = "questionBank")
            }
    )
    public ResultObWrapper saveQuestion(@RequestBody QuestionBank questionBank) {
        return this.questionBankApi.saveQuestion(questionBank);
    }

    @ApiOperation(value = "deleteQuestion.删除试题")
    @PostMapping("deleteQuestion")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "ID", dataType = "String", name = "id")
            }
    )
    public ResultObWrapper deleteQuestion(String id) {
        return this.questionBankApi.deleteQuestion(id);
    }
}
