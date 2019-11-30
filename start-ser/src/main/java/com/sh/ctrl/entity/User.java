package com.sh.ctrl.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 8186309468578172442L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    private String name;

    private String phone;

    private String modelid;

    private String dealerid;

    private String createtime;

    private String updatetime;

}
