package com.sh.ctrl.dao;

import com.sh.ctrl.entity.Models;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModelsDao extends JpaRepository<Models, String> {

    /**
     * 分页查询车型
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    @Query(nativeQuery = true, value = "select * from models limit ?1, ?2")
    List<Models> findAllByPage(Integer page, Integer size);
}
