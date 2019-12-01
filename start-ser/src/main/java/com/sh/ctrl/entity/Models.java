package com.sh.ctrl.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "models")
public class Models implements Serializable {

    private static final long serialVersionUID = 1405070336447985921L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    private String type;

    private String createtime;

    private String updatetime;
}
