package com.sh.ctrl.api;

import com.alibaba.druid.util.StringUtils;
import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.entity.Dealers;
import com.sh.ctrl.service.DealersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DealersApi extends CommonApi<Dealers, String> {

    private DealersService dealersService;

    @Autowired
    public void setDealersService(DealersService dealersService) {
        this.dealersService = dealersService;
    }

    @Override
    public CommonService<Dealers, String> getCommonService() {
        return this.dealersService;
    }

    /**
     * 分页查询经销商
     * @param city 城市名称
     * @param jxsName 经销商名称
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    public ResultObListWrapper<Dealers> findAllByPage(String city, String jxsName, Integer page, Integer size) {
        ResultObListWrapper<Dealers> resultOb = new ResultObListWrapper<>();
        List<Dealers> list = this.dealersService.findAllByPage("%" + city + "%", "%" + jxsName + "%", page - 1, size);
        long count = this.dealersService.count();
        resultOb.setItems(list);
        resultOb.setTotal(count);
        Tools.setSuccessMessage(resultOb, "查询成功");
        return resultOb;
    }

    /**
     * 添加或修改经销商
     * @param dealers 经销商对象
     * @return 略
     */
    public ResultObWrapper<Dealers> saveDealers(Dealers dealers) {
        ResultObWrapper<Dealers> resultObWrapper = new ResultObWrapper<>();
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            dealers.setUpdatetime(date);
            if (StringUtils.isEmpty(dealers.getCreatetime())) {
                dealers.setCreatetime(date);
            }
            this.dealersService.saveDealers(dealers);
            resultObWrapper.setData(dealers);
            if (StringUtils.isEmpty(dealers.getId())) {
                Tools.setSuccessMessage(resultObWrapper, "添加成功");
            } else {
                Tools.setSuccessMessage(resultObWrapper, "修改成功");
            }
        } catch (Exception e) {
            log.error("编辑经销商失败，错误原因： [{}]", e.getMessage());
            if (StringUtils.isEmpty(dealers.getId())) {
                Tools.setErrorMessage(resultObWrapper, "添加失败");
            } else {
                Tools.setErrorMessage(resultObWrapper, "修改失败");
            }
        }
        return resultObWrapper;
    }

    /**
     * 删除经销商
     * @param ids ID
     * @return 略
     */
    public ResultObWrapper<Dealers> deleteDealers(String ids) {
        ResultObWrapper<Dealers> resultObWrapper = new ResultObWrapper<>();
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                this.dealersService.deleteById(id);
            }
            Tools.setSuccessMessage(resultObWrapper, "删除成功");
        } catch (Exception e) {
            log.error("删除经销商失败，错误原因： [{}]", e.getMessage());
            Tools.setErrorMessage(resultObWrapper, "删除失败");
        }
        return resultObWrapper;
    }
}
