package com.sh.ctrl.api;

import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.entity.SysUser;
import com.sh.ctrl.service.SysUserService;
import com.sh.util.CipherUtil;
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

    /**
     * 登录接口
     * @param userName 账号
     * @param password 密码
     * @return 略
     */
    public ResultObWrapper<SysUser> getLogin(String userName, String password) {
        ResultObWrapper<SysUser> resultObWrapper = new ResultObWrapper<>();
        SysUser user = sysUserService.findByUserNameAndPassword(userName, CipherUtil.encryptAesString(password));
        if (null != user) {
            resultObWrapper.setData(user);
            Tools.setSuccessMessage(resultObWrapper, "登录成功！");
        } else {
            Tools.setErrorMessage(resultObWrapper, "登录失败！");
        }
        return resultObWrapper;
    }

    public static void main(String[] args) {
        String str = CipherUtil.encryptAesString("admin");
        System.out.println(str);
        System.out.println(CipherUtil.decryptAesString(str));
    }
}
