package com.sh.common.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * MappedSuperclass 这个注解在我们进行实体映射数据库表时可以作为基类，可以存放一些共有属性字段，例如：createTime, sort, id
 * @author sh 953250192@qq.com
 * @version 2019/4/10 14:25
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3455370344361839253L;
    private String id;

    private Integer state;

    private String ctime;

    private String cdate;

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 1)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Column(length = 10)
    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    @Column(length = 10)
    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}
