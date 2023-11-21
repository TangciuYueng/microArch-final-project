package cn.edu.tongji.diaryWriting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiaryWritingController {
    @RequestMapping("/test")
    public String test() {
        return "Heart wave!";
    }
}
