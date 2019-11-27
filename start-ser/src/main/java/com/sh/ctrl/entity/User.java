package com.sh.ctrl.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 8186309468578172442L;

    @Id
    private String id;

    private String name;

    private String phone;

    private String modelid;

    private String dealerid;

    private String createtime;

    private String updatetime;

}
