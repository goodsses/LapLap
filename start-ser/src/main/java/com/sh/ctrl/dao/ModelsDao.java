package com.sh.ctrl.dao;

import com.sh.ctrl.entity.Models;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelsDao extends JpaRepository<Models, String> {

}
