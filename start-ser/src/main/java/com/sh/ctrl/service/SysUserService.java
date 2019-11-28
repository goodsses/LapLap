package com.sh.ctrl.service;

import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.SysUser;

public interface SysUserService extends CommonService<SysUser, String> {

    /**
     * 根据账号密码查询管理员
     * @param userName 账号
     * @param password 密码
     * @return 略
     */
    SysUser findByUserNameAndPassword(String userName, String password);
}
