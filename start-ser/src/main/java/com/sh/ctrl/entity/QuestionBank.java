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
@Table(name = "questionbank")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = -4871232182604241571L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String question;

    private String optiona;

    private String optionb;

    private String optionaimg;

    private String optionbimg;

    private String answer;

    private String createtime;

    private String updateTime;

    private String prompt;
}
