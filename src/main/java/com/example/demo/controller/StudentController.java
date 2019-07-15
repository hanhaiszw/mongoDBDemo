package com.example.demo.controller;

import com.example.demo.common.MongoManager;
import com.example.demo.model.StatisResult;
import com.example.demo.model.Student;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by sunzhongwei on 2019/07/14
 * Desc:
 **/
@RestController
@EnableAutoConfiguration
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private MongoManager mongoManager;

    @RequestMapping("/count")
    private String getStudentCount() {
        Long count = mongoManager.getCount();
        return "共有" + count + "条数据";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public Student getStudent(@PathVariable long id) {
        Student student = this.mongoManager.getStudentById(id);
        return student;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public List<Student> getAllStudent() {
        List<Student> studentList = this.mongoManager.getAllStudent();
        return studentList;
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET, produces = "application/json")
    public List<StatisResult> getGroupResult() {
        List<StatisResult> statisResultList = this.mongoManager.aggregationTest();
        return statisResultList;
    }

}
