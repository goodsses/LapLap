package com.sh.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.sh.common.bean.PageReq;
import com.sh.common.bean.SortRes;
import com.sh.common.cons.ExceConst;
import com.sh.common.wrapper.ResultBase;
import com.sh.common.wrapper.ResultObWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具类，用处嘛呃············
 *
 * @author lxy 953250192@qq.com
 * @version 2019/4/10 17:38
 */
@Slf4j
public class Tools {

    public static void setSuccessMessage(ResultBase resModel, String msg) {
        resModel.setSuccess(true);
        resModel.setCode(ExceConst.SYSTEM_SUCCESS_MESSAGE);
        resModel.setMessage(msg);
    }

    public static void setSuccessMessage(ResultBase resModel, String code, String msg) {
        resModel.setSuccess(true);
        resModel.setCode(code);
        resModel.setMessage(msg);
    }

    /**
     * <T extends Serializable> 这是个作业哦，自己去查什么意思
     */
    public static <T extends Serializable> ResultObWrapper<T> toSuccessResultOb(List<T> ts) {
        return toResultOb(ts, ts == null ? 0L : (long) ts.size(), true, null, "ok");
    }

    public static <T extends Serializable> ResultObWrapper<T> toSuccessResultOb(T t) {
        return toResultOb(t, t == null, null, "ok");
    }

    public static <T extends Serializable> ResultObWrapper<JSONObject> toSuccessResultOb(JSONObject jsonObject) {
        return toResultOb(jsonObject, jsonObject == null, null, "ok");
    }

    public static void setErrorMessage(ResultBase resModel, String msg) {
        resModel.setSuccess(false);
        resModel.setCode(ExceConst.SYSTEM_ERROR_MESSAGE);
        resModel.setMessage(msg);
    }

    public static void setErrorMessage(ResultBase resModel, String code, String msg) {
        resModel.setSuccess(false);
        resModel.setCode(code);
        resModel.setMessage(msg);
    }

    public static <T extends Serializable> ResultObWrapper<T> toResultOb(T t, boolean success, String code, String msg) {
        ResultObWrapper<T> resultOb = new ResultObWrapper<>();
        resultOb.setData(t);
        if (StringUtils.isBlank(code)) {
            if (success) {
                code = ExceConst.SYSTEM_SUCCESS_CODE;
            } else {
                code = ExceConst.SYSTEM_ERROR_CODE;
            }
        }
        resultOb.setSuccess(success);
        resultOb.setCode(code);
        resultOb.setMessage(msg);
        return resultOb;
    }

    public static <T extends Serializable> ResultObWrapper<T> toResultOb(List<T> ts, Long total, boolean success, String code, String msg) {
        ResultObWrapper<T> resultOb = new ResultObWrapper<>();
        if (ts != null) {
            resultOb.setItems(ts);
        }
        resultOb.setTotal(total);
        if (StringUtils.isBlank(code)) {
            if (success) {
                code = ExceConst.SYSTEM_SUCCESS_CODE;
            } else {
                code = ExceConst.SYSTEM_ERROR_CODE;
            }
        }
        resultOb.setSuccess(success);
        resultOb.setCode(code);
        resultOb.setMessage(msg);
        return resultOb;
    }

    /**
     * 从httprequest请求中获取分页参数
     * @param request 请求
     * @return 分页对象
     */
    public static PageReq pageReqToRequest(HttpServletRequest request) {
        String pageStr = request.getParameter("page"),
            sizeStr = request.getParameter("size"),
            orderBys = request.getParameter("orderBys");
        int page = 0,
            size = 10;
        try {
            page = Math.max(0, Integer.parseInt(pageStr) - 1);
            size = Math.max(1, Integer.parseInt(sizeStr));
        } catch (Exception e) {
            log.error("分页页码~条数转换异常" + e.getMessage());
            System.out.println(e.getMessage());
        }
        return new PageReq(page, size, getSortRes(orderBys));
    }

    /**
     * 获取排序规则
     * @param orderBys 排序原始字符
     * @return 集合
     */
    private static List<SortRes> getSortRes(String orderBys) {
        List<SortRes> sortResList = new ArrayList<>();
        if (StringUtils.isNotBlank(orderBys)) {
            for (String order : orderBys.split(",")) {
                String[] orders = order.split("\\|");
                SortRes sortRes = new SortRes();
                sortRes.setName(orders[0]);
                sortRes.setDir(orders[1]);
                sortResList.add(sortRes);
            }
        }
        return sortResList;
    }
}
