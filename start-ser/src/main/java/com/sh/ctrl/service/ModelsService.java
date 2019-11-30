package com.sh.ctrl.service;

import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.Models;
import java.util.List;

public interface ModelsService extends CommonService<Models, String> {

    /**
     * 查询车型分页
     * @param type 类型
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    List<Models> findAllByPage(String type, Integer page, Integer size);

    /**
     * 添加或修改车型
     * @param models 车型对象
     */
    void saveModels(Models models);
}
