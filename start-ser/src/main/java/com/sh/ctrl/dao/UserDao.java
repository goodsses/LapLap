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
     * @param name 名称
     * @param mobile 手机号
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    @Query(nativeQuery = true, value = "select u.* from user u where u.NAME like ?1 and u.PHONE like ?2 limit ?3, ?4")
    List<User> findAllByPage(String name, String mobile, Integer page, Integer size);

    User findByPhone(String phone);
}
