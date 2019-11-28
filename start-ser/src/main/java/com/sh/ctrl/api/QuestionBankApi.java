package com.sh.ctrl.api;

import com.alibaba.druid.util.StringUtils;
import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.entity.QuestionBank;
import com.sh.ctrl.service.QuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class QuestionBankApi extends CommonApi<QuestionBank, String> {

    private QuestionBankService questionBankService;

    @Autowired
    public void setQuestionBankService(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    @Override
    public CommonService<QuestionBank, String> getCommonService() {
        return this.questionBankService;
    }

    /**
     * 分页查询试题
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    public ResultObListWrapper<QuestionBank> findAllByPage(Integer page, Integer size) {
        ResultObListWrapper<QuestionBank> resultOb = new ResultObListWrapper<>();
        List<QuestionBank> list = this.questionBankService.findAllByPage(page - 1, size);
        long count = this.questionBankService.count();
        resultOb.setItems(list);
        resultOb.setTotal(count);
        Tools.setSuccessMessage(resultOb, "查询成功");
        return resultOb;
    }

    /**
     * 添加或修改试题
     * @param questionBank 试题对象
     * @return 略
     */
    public ResultObWrapper<QuestionBank> saveQuestion(QuestionBank questionBank) {
        ResultObWrapper<QuestionBank> resultObWrapper = new ResultObWrapper<>();
        try {
            this.questionBankService.saveQuestion(questionBank);
            resultObWrapper.setData(questionBank);
            if (StringUtils.isEmpty(questionBank.getId())) {
                Tools.setSuccessMessage(resultObWrapper, "添加成功");
            } else {
                Tools.setSuccessMessage(resultObWrapper, "修改成功");
            }
        } catch (Exception e) {
            log.error("编辑试题失败，错误原因： [{}]", e.getMessage());
            if (StringUtils.isEmpty(questionBank.getId())) {
                Tools.setErrorMessage(resultObWrapper, "添加失败");
            } else {
                Tools.setErrorMessage(resultObWrapper, "修改失败");
            }
        }
        return resultObWrapper;
    }

    /**
     * 删除试题
     * @param id ID
     * @return 略
     */
    public ResultObWrapper<QuestionBank> deleteQuestion(String id) {
        ResultObWrapper<QuestionBank> resultObWrapper = new ResultObWrapper<>();
        try {
            this.questionBankService.deleteById(id);
            Tools.setSuccessMessage(resultObWrapper, "删除成功");
        } catch (Exception e) {
            log.error("删除试题失败，错误原因： [{}]", e.getMessage());
            Tools.setErrorMessage(resultObWrapper, "删除失败");
        }
        return resultObWrapper;
    }
}
