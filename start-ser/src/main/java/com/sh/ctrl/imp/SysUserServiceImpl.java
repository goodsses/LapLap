package com.sh.ctrl.imp;

import com.sh.common.impl.CommonServiceImpl;
import com.sh.ctrl.dao.SysUserDao;
import com.sh.ctrl.entity.SysUser;
import com.sh.ctrl.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends CommonServiceImpl<SysUser, String> implements SysUserService {

    private SysUserDao sysUserDao;

    @Autowired
    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Override
    public JpaRepository<SysUser, String> getJpaRepository() {
        return this.sysUserDao;
    }
}
