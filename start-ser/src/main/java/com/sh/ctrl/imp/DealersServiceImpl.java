package com.sh.ctrl.imp;

import com.sh.common.impl.CommonServiceImpl;
import com.sh.ctrl.dao.DealersDao;
import com.sh.ctrl.entity.Dealers;
import com.sh.ctrl.service.DealersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
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

    @Override
    public List<Dealers> findAllByPage(String city, String jxsName, Integer page, Integer size) {
        return this.dealersDao.findAllByPage(city, jxsName, page, size);
    }

    @Override
    public void saveDealers(Dealers dealers) {
        this.dealersDao.save(dealers);
    }

    @Override
    public List<String> getAllCity() {
        return this.dealersDao.findAllCity();
    }

    @Override
    public List<Dealers> getAllByCity(String city) {
        return this.dealersDao.findAllByCity(city);
    }
}
