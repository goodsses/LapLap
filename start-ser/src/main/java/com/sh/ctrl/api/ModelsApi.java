package com.sh.ctrl.api;

import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.Models;
import com.sh.ctrl.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
