package com.sh.ctrl.dao;

import com.sh.ctrl.entity.Dealers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DealersDao extends JpaRepository<Dealers, String> {

    /**
     * 分页查询经销商
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    @Query(nativeQuery = true, value = "select * from dealers limit ?1, ?2")
    List<Dealers> findAllByPage(Integer page, Integer size);
}
