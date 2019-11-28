package com.sh.ctrl.dao;

import com.sh.ctrl.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, String> {

    /**
     * 根据账号密码查询管理员
     * @param userName 账号
     * @param password 密码
     * @return 略
     */
    SysUser findByUserNameAndPassword(String userName, String password);
}
