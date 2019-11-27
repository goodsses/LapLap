package com.sh.common.bean;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lombok 具体博客 ： https://blog.csdn.net/u013225178/article/details/80721799
 * NoArgsConstructor 空构造函数
 * Data == Setter && Getter
 * AllArgsConstructor == 所有字段共同生成构造
 * @author sh 953250192@qq.com
 * @version 2019/4/10 16:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageReq implements Serializable {

    private static final long serialVersionUID = 7069766592782752185L;

    private int page;

    private int size;

    private List<SortRes> sorts;
}
