package com.sh.common.wrapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/10 16:45
 */
@ApiModel(value = "返参对象类")
@Getter
@Setter
public class ResultObWrapper<T extends Serializable> extends ResultBase{

    private static final long serialVersionUID = 2359282937357817558L;

    @ApiModelProperty(value = "返回条数", dataType = "Long")
    private Long total;

    @ApiModelProperty(value = "返回List集合, //实体集合", dataType = "Object")
    private List<T> items;

    @ApiModelProperty(value = "返回对象, //实体对象", dataType = "Object")
    private T data;

    @ApiModelProperty(value = "返回Map集合, //实体集合", dataType = "Object")
    private Map<String, Object> info;
}
