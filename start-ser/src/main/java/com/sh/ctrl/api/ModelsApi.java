package com.sh.ctrl.api;

import com.alibaba.druid.util.StringUtils;
import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObListWrapper;
import com.sh.common.wrapper.ResultObWrapper;
import com.sh.ctrl.entity.Models;
import com.sh.ctrl.service.ModelsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ModelsApi extends CommonApi<Models, String> {

    private ModelsService modelsService;

    @Autowired
    public void setModelsService(ModelsService modelsService) {
        this.modelsService = modelsService;
    }

    @Override
    public CommonService<Models, String> getCommonService() {
        return this.modelsService;
    }

    /**
     * 分页查询车型
     * @param type 类型
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    public ResultObListWrapper<Models> findAllByPage(String type, Integer page, Integer size) {
        ResultObListWrapper<Models> resultOb = new ResultObListWrapper<>();
        List<Models> list = this.modelsService.findAllByPage("%" + type + "%", page - 1, size);
        long count = this.modelsService.count();
        resultOb.setItems(list);
        resultOb.setTotal(count);
        Tools.setSuccessMessage(resultOb, "查询成功");
        return resultOb;
    }

    /**
     * 添加或修改车型
     * @param models 车型对象
     * @return 略
     */
    public ResultObWrapper<Models> saveModels(Models models) {
        ResultObWrapper<Models> resultObWrapper = new ResultObWrapper<>();
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            models.setUpdatetime(date);
            if (StringUtils.isEmpty(models.getCreatetime())) {
                models.setCreatetime(date);
            }
            this.modelsService.saveModels(models);
            resultObWrapper.setData(models);
            if (StringUtils.isEmpty(models.getId())) {
                Tools.setSuccessMessage(resultObWrapper, "添加成功");
            } else {
                Tools.setSuccessMessage(resultObWrapper, "修改成功");
            }
        } catch (Exception e) {
            log.error("编辑车型失败，错误原因： [{}]", e.getMessage());
            if (StringUtils.isEmpty(models.getId())) {
                Tools.setErrorMessage(resultObWrapper, "添加失败");
            } else {
                Tools.setErrorMessage(resultObWrapper, "修改失败");
            }
        }
        return resultObWrapper;
    }

    /**
     * 删除车型
     * @param ids ID
     * @return 略
     */
    public ResultObWrapper<Models> deleteModels(String ids) {
        ResultObWrapper<Models> resultObWrapper = new ResultObWrapper<>();
        try {
            String[] idList = ids.split(",");
            for (String id : idList) {
                this.modelsService.deleteById(id);
            }
            Tools.setSuccessMessage(resultObWrapper, "删除成功");
        } catch (Exception e) {
            log.error("删除车型失败，错误原因： [{}]", e.getMessage());
            Tools.setErrorMessage(resultObWrapper, "删除失败");
        }
        return resultObWrapper;
    }
}
