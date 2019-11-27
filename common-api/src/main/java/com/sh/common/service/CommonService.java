package com.sh.common.service;

import com.sh.common.bean.PageReq;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 此接口作用是service接口集成，目的是减少重复方法的编写，让开发变得更高效
 * @author sh 953250192@qq.com
 * @version 2019/4/10 15:53
 */
public interface CommonService<T extends Serializable, ID extends Serializable> {

    JpaRepository<T, ID> getJpaRepository();

    //--------------------------------查

    List<T> findAll();

    List<T> findAll(T var1, PageReq pageReq);

    List<T> findAllById(List<ID> var1);

    T findById(ID var1);

    //--------------------------------增

    T save(T var1);

    List<T> saveAll(List<T> var1);

    //--------------------------------删

    void deleteAll();

    void deleteAll(List<T> var1);

    void deleteById(ID var1);

    void delete(T var1);

    //--------------------------------改

    T saveOrUpdate(T var1);

    List<T> saveOrUpdateAll(List<T> var1);

    //--------------------------------常用方法 例如：取数量，验证是否存在

    boolean existsById(ID var1);

    boolean exists(T var1);

    long count(T var1);

    long count();
}
