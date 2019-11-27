package com.sh.ctrl.dao;

import com.sh.ctrl.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBankDao extends JpaRepository<QuestionBank, String> {

}
