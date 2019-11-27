package com.sh.ctrl.dao;

import com.sh.ctrl.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, String> {

}
