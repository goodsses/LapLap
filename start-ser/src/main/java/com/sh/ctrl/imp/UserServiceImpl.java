package com.sh.ctrl.imp;

import com.sh.common.impl.CommonServiceImpl;
import com.sh.ctrl.dao.UserDao;
import com.sh.ctrl.entity.User;
import com.sh.ctrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends CommonServiceImpl<User, String> implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public JpaRepository<User, String> getJpaRepository() {
        return this.userDao;
    }

    @Override
    public List<User> findAllByPage(String name, String mobile, Integer page, Integer size) {
        return this.userDao.findAllByPage(name, mobile, page, size);
    }

    @Override
    public void saveUser(User user) {
        this.userDao.save(user);
    }

    @Override
    public User findByPhone(String phone) {
        return this.userDao.findByPhone(phone);
    }
}
