package com.sh.ctrl.api;

import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.QuestionBank;
import com.sh.ctrl.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
