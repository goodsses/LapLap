package com.sh.ctrl.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 2055650800518687234L;

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "username", length = 50)
    private String userName;

    @Column(name = "password", length = 60)
    private String password;
}
