package com.sh.ctrl.imp;

import com.sh.common.impl.CommonServiceImpl;
import com.sh.ctrl.dao.DealersDao;
import com.sh.ctrl.entity.Dealers;
import com.sh.ctrl.service.DealersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DealersServiceImpl extends CommonServiceImpl<Dealers, String> implements DealersService {

    private DealersDao dealersDao;

    @Autowired
    public void setDealersDao(DealersDao dealersDao) {
        this.dealersDao = dealersDao;
    }

    @Override
    public JpaRepository<Dealers, String> getJpaRepository() {
        return this.dealersDao;
    }
}
