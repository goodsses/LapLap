package com.sh.ctrl.imp;

import com.sh.common.impl.CommonServiceImpl;
import com.sh.ctrl.dao.QuestionBankDao;
import com.sh.ctrl.entity.QuestionBank;
import com.sh.ctrl.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class QuestionBankServiceImpl extends CommonServiceImpl<QuestionBank, String> implements QuestionBankService {

    private QuestionBankDao questionBankDao;

    @Autowired
    public void setQuestionBankDao(QuestionBankDao questionBankDao) {
        this.questionBankDao = questionBankDao;
    }

    @Override
    public JpaRepository<QuestionBank, String> getJpaRepository() {
        return this.questionBankDao;
    }

    @Override
    public List<QuestionBank> findAllByPage(String question, Integer page, Integer size) {
        return this.questionBankDao.findAllByPage(question, page, size);
    }

    @Override
    public void saveQuestion(QuestionBank questionBank) {
        this.questionBankDao.save(questionBank);
    }

    @Override
    public QuestionBank findByQuestion(String question) {
        return this.questionBankDao.findByQuestion(question);
    }

    @Override
    public List<QuestionBank> findAllQuestion() {
       return  this.questionBankDao.findAllQuestion();
    }
}
