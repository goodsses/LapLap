package com.sh.ctrl.service;

import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.Dealers;
import java.util.List;

public interface DealersService extends CommonService<Dealers, String> {

    /**
     * 查询经销商分页
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    List<Dealers> findAllByPage(Integer page, Integer size);

    /**
     * 添加或修改经销商
     * @param dealers 经销商对象
     */
    void saveDealers(Dealers dealers);
}
