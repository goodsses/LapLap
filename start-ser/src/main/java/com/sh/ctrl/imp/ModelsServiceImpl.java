package com.sh.ctrl.imp;

import com.sh.common.impl.CommonServiceImpl;
import com.sh.ctrl.dao.ModelsDao;
import com.sh.ctrl.entity.Models;
import com.sh.ctrl.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModelsServiceImpl extends CommonServiceImpl<Models, String> implements ModelsService {

    private ModelsDao modelsDao;

    @Autowired
    public void setModelsDao(ModelsDao modelsDao) {
        this.modelsDao = modelsDao;
    }

    @Override
    public JpaRepository<Models, String> getJpaRepository() {
        return this.modelsDao;
    }

    @Override
    public List<Models> findAllByPage(Integer page, Integer size) {
        return this.modelsDao.findAllByPage(page, size);
    }

    @Override
    public void saveModels(Models models) {
        this.modelsDao.save(models);
    }
}
