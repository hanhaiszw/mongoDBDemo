package com.example.demo.model;


import lombok.Data;
import org.bson.Document;


/**
 * create by sunzhongwei on 2019/07/13
 * Desc:
 **/
@Data
public class Student {
    Long id;
    String name;
    Integer score;
    String interest;
    Integer grade;

    public Student(Long id, String name, Integer score, String interest, Integer grade) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.interest = interest;
        this.grade = grade;
    }

    public Student(Document document){
        this.id = document.getLong("id");
        this.name = document.getString("name");
        this.score = document.getInteger("score");
        this.interest = document.getString("interest");
        this.grade = document.getInteger("grade");
    }
}
