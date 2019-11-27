package com.sh.common.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * @author sh 953250192@qq.com
 * @version 2019/4/10 16:08
 */
@Data
public class SortRes implements Serializable {

    private static final long serialVersionUID = 341289524825734662L;

    private String name;

    private String dir;
}
