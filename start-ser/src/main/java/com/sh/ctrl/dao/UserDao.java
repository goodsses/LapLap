package com.sh.ctrl.dao;

import com.sh.ctrl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, String> {

    /**
     * 分页查询用户
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    @Query(nativeQuery = true, value = "select * from user limit ?1, ?2")
    List<User> findAllByPage(Integer page, Integer size);
}
