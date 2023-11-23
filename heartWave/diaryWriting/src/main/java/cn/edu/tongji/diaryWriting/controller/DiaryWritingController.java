package cn.edu.tongji.diaryWriting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//对外提供接口
@RestController
public class DiaryWritingController {
    @RequestMapping("/test")
    public String test() {
        return "Heart wave!";
    }
}
