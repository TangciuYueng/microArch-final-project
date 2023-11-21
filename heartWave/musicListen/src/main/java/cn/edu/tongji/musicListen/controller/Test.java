package cn.edu.tongji.musicListen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @RequestMapping("/sjm")
    public String Test(){
        return "hello world!";
    }

}
