package com.sh.ctrl.api;

import com.sh.common.api.CommonApi;
import com.sh.common.service.CommonService;
import com.sh.ctrl.entity.Dealers;
import com.sh.ctrl.service.DealersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
