package com.sh.controller;

import com.alibaba.fastjson.JSONObject;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.api.QuestionBankApi;
import com.sh.ctrl.entity.QuestionBank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "问卷控制器")
@RequestMapping("/questionBank")
public class QuestionBankController {

    private QuestionBankApi questionBankApi;

    @Value("${upload.address}")
    private String uploadAddress;

    @Autowired
    public void setQuestionBankApi(QuestionBankApi questionBankApi) {
        this.questionBankApi = questionBankApi;
    }

    @ApiOperation(value = "findAll.查询所有")
    @PostMapping("findAll")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "题目", dataType = "String", name = "question"),
                    @ApiImplicitParam(value = "页码", dataType = "String", name = "page"),
                    @ApiImplicitParam(value = "数量", dataType = "String", name = "size")
            }
    )
    public ResultObListWrapper findAll(String question, Integer page, Integer size) {
        return this.questionBankApi.findAllByPage(question, page, size);
    }

    @ApiOperation(value = "saveQuestion.添加或修改试题")
    @PostMapping("saveQuestion")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "实体", dataType = "Object", name = "bean")
            }
    )
    public ResultObWrapper saveQuestion(@RequestParam(value = "bean") String bean, @RequestParam(value = "type") String type, @RequestParam(value = "file") MultipartFile file) {
        ResultObWrapper resultObWrapper = new ResultObWrapper();
        Tools.setSuccessMessage(resultObWrapper, "上传成功");
        String fileName = file.getOriginalFilename();
        if (null != fileName) {
            try {
                // 后缀: "jpg || png"
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                List<String> prefixList = Arrays.asList("jpg", "png");
                if (!prefixList.contains(prefix)) {
                    Tools.setErrorMessage(resultObWrapper, "文件格式不正确");
                    return resultObWrapper;
                }
                String date = String.valueOf(new Date().getTime());
                if (!new File(uploadAddress).exists()) {
                    new File(uploadAddress).mkdir();
                }
                File files = new File(uploadAddress + File.separator + date + "_" + fileName);
                file.transferTo(files);
                QuestionBank questionBank = JSONObject.parseObject(bean, QuestionBank.class);
                if ("1".equals(type)) {
                    questionBank.setOptionaimg("/" + date + "_" + fileName);
                } else {
                    questionBank.setOptionbimg("/" + date + "_" + fileName);
                }
                return this.questionBankApi.saveQuestion(questionBank, type);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Tools.setErrorMessage(resultObWrapper, "上传失败");
            }
        } else {
            Tools.setErrorMessage(resultObWrapper, "上传失败");
        }
        return resultObWrapper;
    }

    @ApiOperation(value = "deleteQuestion.删除试题")
    @PostMapping("deleteQuestion")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "ID", dataType = "String", name = "id")
            }
    )
    public ResultObWrapper deleteQuestion(String ids) {
        return this.questionBankApi.deleteQuestion(ids);
    }

}
