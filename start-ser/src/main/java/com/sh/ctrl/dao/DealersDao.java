package com.sh.ctrl.dao;

import com.sh.ctrl.entity.Dealers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealersDao extends JpaRepository<Dealers, String> {

}
