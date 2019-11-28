package com.sh.ctrl.api;

import com.alibaba.druid.util.StringUtils;
import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.entity.User;
import com.sh.ctrl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class UserApi extends CommonApi<User, String> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommonService<User, String> getCommonService() {
        return this.userService;
    }

    /**
     * 分页查询用户
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    public ResultObListWrapper<User> findAllByPage(Integer page, Integer size) {
        ResultObListWrapper<User> resultOb = new ResultObListWrapper<>();
        List<User> list = this.userService.findAllByPage(page - 1, size);
        long count = this.userService.count();
        resultOb.setItems(list);
        resultOb.setTotal(count);
        Tools.setSuccessMessage(resultOb, "查询成功");
        return resultOb;
    }

    /**
     * 添加或修改用户
     * @param user 用户对象
     * @return 略
     */
    public ResultObWrapper<User> saveUser(User user) {
        ResultObWrapper<User> resultObWrapper = new ResultObWrapper<>();
        try {
            this.userService.saveUser(user);
            resultObWrapper.setData(user);
            if (StringUtils.isEmpty(user.getId())) {
                Tools.setSuccessMessage(resultObWrapper, "添加成功");
            } else {
                Tools.setSuccessMessage(resultObWrapper, "修改成功");
            }
        } catch (Exception e) {
            log.error("编辑用户失败，错误原因： [{}]", e.getMessage());
            if (StringUtils.isEmpty(user.getId())) {
                Tools.setErrorMessage(resultObWrapper, "添加失败");
            } else {
                Tools.setErrorMessage(resultObWrapper, "修改失败");
            }
        }
        return resultObWrapper;
    }

    /**
     * 删除用户
     * @param id ID
     * @return 略
     */
    public ResultObWrapper<User> deleteUser(String id) {
        ResultObWrapper<User> resultObWrapper = new ResultObWrapper<>();
        try {
            this.userService.deleteById(id);
            Tools.setSuccessMessage(resultObWrapper, "删除成功");
        } catch (Exception e) {
            log.error("删除用户失败，错误原因： [{}]", e.getMessage());
            Tools.setErrorMessage(resultObWrapper, "删除失败");
        }
        return resultObWrapper;
    }
}
