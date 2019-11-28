package com.sh.ctrl.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "dealers")
public class Dealers implements Serializable {

    private static final long serialVersionUID = 2967979049609729278L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String city;

    private String code;

    private String jxsname;

    private String jxsjc;

    private String createtime;

    private String updatetime;
}
