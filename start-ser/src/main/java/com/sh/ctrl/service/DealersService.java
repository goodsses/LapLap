package com.sh.ctrl.service;

import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.Dealers;
import java.util.List;

public interface DealersService extends CommonService<Dealers, String> {

    /**
     * 查询经销商分页
     * @param city 城市名称
     * @param jxsName 经销商名称
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    List<Dealers> findAllByPage(String city, String jxsName, Integer page, Integer size);

    /**
     * 添加或修改经销商
     * @param dealers 经销商对象
     */
    void saveDealers(Dealers dealers);

    /**
     * 获取所有城市
     * @return
     */
    List<String> getAllCity();

    /**
     * 根据城市获取经销商
     * @param city
     * @return
     */
    List<Dealers> getAllByCity(String city);
}
