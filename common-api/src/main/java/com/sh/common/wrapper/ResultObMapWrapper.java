package com.sh.common.wrapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/10 17:22
 */
@Getter
@Setter
@ApiModel(value = "返参单独Map类")
public class ResultObMapWrapper<T extends Serializable> extends ResultBase {

    private static final long serialVersionUID = -8498743043908763128L;

    @ApiModelProperty(value = "返回Map集合, //实体集合", dataType = "Object")
    private Map<String, Object> map;
}
