package com.example.demo.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by sunzhongwei on 2019/07/13
 * Desc:
 **/
@RestController
@EnableAutoConfiguration
public class EntryController {
    @RequestMapping("/")
    private String index(){
        return "这是一个mybatis示例代码!";
    }
}
