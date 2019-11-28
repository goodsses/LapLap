package com.sh.ctrl.dao;

import com.sh.ctrl.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionBankDao extends JpaRepository<QuestionBank, String> {

    /**
     * 分页查询试题
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    @Query(nativeQuery = true, value = "select * from questionbank limit ?1, ?2")
    List<QuestionBank> findAllByPage(Integer page, Integer size);
}
