package com.sh.common.wrapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/10 16:50
 */
@Data
@ApiModel(value = "返参基类")
public class ResultBase implements Serializable {

    private static final long serialVersionUID = -5010525564448201317L;

    @ApiModelProperty(value = "返回编码, ^\\\\d{4,8}$", dataType = "String")
    private String code;

    @ApiModelProperty(value = "是否成功，true \\| false", dataType = "boolean")
    private boolean success;

    @ApiModelProperty(value = "返回描述, //todo", dataType = "String")
    private String message;

}
