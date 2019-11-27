package com.sh.ctrl.api;

import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.SysUser;
import com.sh.ctrl.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserApi extends CommonApi<SysUser, String> {

    private SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public CommonService<SysUser, String> getCommonService() {
        return this.sysUserService;
    }
}
