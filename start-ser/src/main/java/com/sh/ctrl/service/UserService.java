package com.sh.ctrl.service;

import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.User;
import java.util.List;

public interface UserService extends CommonService<User, String> {

    /**
     * 查询用户分页
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    List<User> findAllByPage(Integer page, Integer size);

    /**
     * 添加或修改用户
     * @param user 用户对象
     */
    void saveUser(User user);
}
