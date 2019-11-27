package com.sh.common.api;

import com.alibaba.fastjson.JSONObject;
import com.sh.common.bean.PageReq;
import com.sh.common.service.CommonService;
import com.sh.common.utils.Tools;
import com.sh.common.wrapper.ResultObWrapper;
import java.io.Serializable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/11 10:41
 */
@Slf4j
public abstract class CommonApi<T extends Serializable, ID extends Serializable> {

    public abstract CommonService<T, ID> getCommonService();

    public ResultObWrapper<T> findAll() {
        List<T> tList = getCommonService().findAll();
        return Tools.toSuccessResultOb(tList);
    }

    public ResultObWrapper<T> findAll(T var1, PageReq pageReq) {
        List<T> tList = getCommonService().findAll(var1, pageReq);
        return Tools.toSuccessResultOb(tList);
    }

    public ResultObWrapper<T> findById(ID var1) {
        T t = getCommonService().findById(var1);
        return Tools.toSuccessResultOb(t);
    }

    //--------------------------------增

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<T> save(T var1) {
        T t = getCommonService().save(var1);
        return Tools.toSuccessResultOb(t);
    }

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<T> saveAll(List<T> var1) {
        List<T> tList = getCommonService().saveAll(var1);
        return Tools.toSuccessResultOb(tList);
    }

    //--------------------------------删

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<JSONObject> deleteAll() {
        JSONObject jsonObject = new JSONObject();
        try {
            getCommonService().deleteAll();
            jsonObject.put("flag", true);
        } catch (Exception e) {
            log.error("commonApi==={}", e.getMessage());
            jsonObject.put("flag", true);
        }
        return Tools.toSuccessResultOb(jsonObject);
    }

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<JSONObject> deleteAll(List<T> var1) {
        JSONObject jsonObject = new JSONObject();
        try {
            getCommonService().deleteAll(var1);
            jsonObject.put("flag", true);
        } catch (Exception e) {
            log.error("commonApi==={}", e.getMessage());
            jsonObject.put("flag", true);
        }
        return Tools.toSuccessResultOb(jsonObject);
    }

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<JSONObject> deleteById(ID var1) {
        JSONObject jsonObject = new JSONObject();
        try {
            getCommonService().deleteById(var1);
            jsonObject.put("flag", true);
        } catch (Exception e) {
            log.error("commonApi==={}", e.getMessage());
            jsonObject.put("flag", true);
        }
        return Tools.toSuccessResultOb(jsonObject);
    }

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<JSONObject> delete(T var1) {
        JSONObject jsonObject = new JSONObject();
        try {
            getCommonService().delete(var1);
            jsonObject.put("flag", true);
        } catch (Exception e) {
            log.error("commonApi==={}", e.getMessage());
            jsonObject.put("flag", true);
        }
        return Tools.toSuccessResultOb(jsonObject);
    }

    //--------------------------------改

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<T> saveOrUpdate(T var1){
        T t = getCommonService().saveOrUpdate(var1);
        return Tools.toSuccessResultOb(t);
    }

    @Transactional(rollbackFor = Exception.class)
    ResultObWrapper<T> saveOrUpdateAll(List<T> var1){
        List<T> tList = getCommonService().saveOrUpdateAll(var1);
        return Tools.toSuccessResultOb(tList);
    }

    //--------------------------------常用方法 例如：取数量，验证是否存在

    boolean existsById(ID var1){
        return getCommonService().existsById(var1);
    }

    boolean exists(T var1){
        return getCommonService().exists(var1);
    }

    long count(T var1){
        return getCommonService().count(var1);
    }

    long count(){
        return getCommonService().count();
    }
}
