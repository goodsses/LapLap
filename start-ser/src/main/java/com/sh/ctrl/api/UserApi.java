package com.sh.ctrl.api;

import com.alibaba.druid.util.StringUtils;
import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.entity.Dealers;
import com.sh.ctrl.entity.Models;
import com.sh.ctrl.entity.UserWrapper;
import com.sh.ctrl.entity.User;
import com.sh.ctrl.service.DealersService;
import com.sh.ctrl.service.ModelsService;
import com.sh.ctrl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserApi extends CommonApi<User, String> {

    private UserService userService;

    private DealersService dealersService;

    private ModelsService modelsService;

    @Autowired
    public void setModelsService(ModelsService modelsService) {
        this.modelsService = modelsService;
    }

    @Autowired
    public void setDealersService(DealersService dealersService) {
        this.dealersService = dealersService;
    }

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
     * @param name 名称
     * @param mobile 手机号
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    public ResultObListWrapper<UserWrapper> findAllByPage(String name, String mobile, Integer page, Integer size) {
        ResultObListWrapper<UserWrapper> resultOb = new ResultObListWrapper<>();
        List<User> list = this.userService.findAllByPage("%" + name + "%", "%" + mobile + "%", page - 1, size);
        List<UserWrapper> userWrappers = list.stream().map(item -> {
            UserWrapper userWrapper = new UserWrapper();
            BeanUtils.copyProperties(item, userWrapper);
            try {
                Dealers dealers = dealersService.findById(item.getDealerid());
                if (null != dealers) {
                    userWrapper.setCity(dealers.getCity());
                    userWrapper.setJxsjc(dealers.getJxsjc());
                    userWrapper.setJxsname(dealers.getJxsname());
                }
            } catch (Exception e) {
                log.error("分页查询用户未查到关联表经销商信息，错误原因： [{}]", e.getMessage());
            }
            try {
                Models models = modelsService.findById(item.getModelid());
                if (null != models) {
                    userWrapper.setType(models.getType());
                }
            } catch (Exception e) {
                log.error("分页查询用户未查到关联表车型信息，错误原因： [{}]", e.getMessage());
            }
            return userWrapper;
        }).collect(Collectors.toList());
        long count = this.userService.count();
        resultOb.setItems(userWrappers);
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
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            user.setCreatetime(date);
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
     * @param ids ID
     * @return 略
     */
    public ResultObWrapper<User> deleteUser(String ids) {
        ResultObWrapper<User> resultObWrapper = new ResultObWrapper<>();
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                this.userService.deleteById(id);
            }
            Tools.setSuccessMessage(resultObWrapper, "删除成功");
        } catch (Exception e) {
            log.error("删除用户失败，错误原因： [{}]", e.getMessage());
            Tools.setErrorMessage(resultObWrapper, "删除失败");
        }
        return resultObWrapper;
    }
}
