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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionBankApi extends CommonApi<QuestionBank, String> {

    private QuestionBankService questionBankService;

    @Value("${upload.address}")
    private String uploadAddress;

    @Value("${show.address}")
    private String showAddress;

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
     * @param question 题目
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    public ResultObListWrapper<QuestionBank> findAllByPage(String question, Integer page, Integer size) {
        ResultObListWrapper<QuestionBank> resultOb = new ResultObListWrapper<>();
        List<QuestionBank> list = this.questionBankService.findAllByPage("%" + question + "%", (page - 1) * size, size);
        long count = this.questionBankService.count();
        list = list.stream().peek(item -> {
            if (null != item.getOptionaimg()) {
                item.setOptionaimg(showAddress + item.getOptionaimg());
            }
            if (null != item.getOptionbimg()) {
                item.setOptionbimg(showAddress + item.getOptionbimg());
            }
        }).collect(Collectors.toList());
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
    public synchronized ResultObWrapper<QuestionBank> saveQuestion(QuestionBank questionBank, String type) {
        ResultObWrapper<QuestionBank> resultObWrapper = new ResultObWrapper<>();
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            questionBank.setUpdatetime(date);
            if (StringUtils.isEmpty(questionBank.getCreatetime())) {
                questionBank.setCreatetime(date);
            }
            QuestionBank questionBankNew = questionBankService.findByQuestion(questionBank.getQuestion());
            if(null != questionBankNew) {
                questionBank.setId(questionBankNew.getId());
                if ("1".equals(type)) {
                    questionBank.setOptionbimg(questionBankNew.getOptionbimg());
                } else {
                    questionBank.setOptionaimg(questionBankNew.getOptionaimg());
                }
            }
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
     * @param ids ID
     * @return 略
     */
    public ResultObWrapper<QuestionBank> deleteQuestion(String ids) {
        ResultObWrapper<QuestionBank> resultObWrapper = new ResultObWrapper<>();
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                this.questionBankService.deleteById(id);
            }
            Tools.setSuccessMessage(resultObWrapper, "删除成功");
        } catch (Exception e) {
            log.error("删除试题失败，错误原因： [{}]", e.getMessage());
            Tools.setErrorMessage(resultObWrapper, "删除失败");
        }
        return resultObWrapper;
    }
}
