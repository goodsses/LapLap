package com.sh.ctrl.service;

import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.QuestionBank;

import java.util.List;

public interface QuestionBankService extends CommonService<QuestionBank, String> {

    /**
     * 查询试题分页
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    List<QuestionBank> findAllByPage(Integer page, Integer size);

    /**
     * 添加或修改试题
     * @param questionBank 试题对象
     */
    void saveQuestion(QuestionBank questionBank);
}
