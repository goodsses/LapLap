package com.sh.ctrl.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "questionbank")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = -4871232182604241571L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    private String question;

    private String optiona;

    private String optionb;

    private String optionaimg;

    private String optionbimg;

    private String answer;

    private String createtime;

    private String updatetime;

    private String prompt;
}
