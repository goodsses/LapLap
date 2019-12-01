package com.sh.ctrl.entity;

import com.sh.common.anno.Excel;
import com.sh.common.anno.ExcelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@Excel(sheetName = "用户信息统计表", header = {"用户名称", "手机号", "车型", "城市", "经销商名称", "创建时间"})
public class UserInfoWrapper implements Serializable {

    private static final long serialVersionUID = 3380972691492778277L;

    @ExcelProperty(index = 0, cellType = 1)
    private String name;

    @ExcelProperty(index = 1, cellType = 1)
    private String phone;

    @ExcelProperty(index = 2, cellType = 1)
    private String modelName;

    @ExcelProperty(index = 3, cellType = 1)
    private String city;

    @ExcelProperty(index = 4, cellType = 1)
    private String jxsName;

    @ExcelProperty(index = 5, cellType = 1)
    private String createTime;
}
