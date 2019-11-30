package com.sh.ctrl.dao;

import com.sh.ctrl.entity.Dealers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DealersDao extends JpaRepository<Dealers, String> {

    /**
     * 分页查询经销商
     * @param city 城市名称
     * @param jxsName 经销商名称
     * @param page 页码
     * @param size 数量
     * @return 略
     */
    @Query(nativeQuery = true, value = "select d.* from dealers d where d.CITY like ?1 and d.jxsname like ?2 order by d.CREATETIME desc limit ?3, ?4")
    List<Dealers> findAllByPage(String city, String jxsName, Integer page, Integer size);

    /**
     * 查询所有城市
     * @return
     */
    @Query(nativeQuery = true, value = "select distinct city from dealers")
    List<String> findAllCity();

    /**
     * 根据城市获取所有经销商
     * @param city
     * @return
     */
    @Query(nativeQuery = true, value = "select * from dealers where city =?1")
    List<Dealers> findAllByCity(String city);
}
