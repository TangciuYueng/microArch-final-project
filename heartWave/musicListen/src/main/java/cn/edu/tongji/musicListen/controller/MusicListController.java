package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.MusicListService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/musiclisten/musiclist")
public class MusicListController {
//    @Resource
//    private List<MusicList> allMusicList = new ArrayList<>();
    @Resource
    private MusicListService musicListService;
    @PostMapping
    public int insertMusicList(@RequestBody MusicList musicList) {
        try {
            System.out.println("contrl" + musicList);
            return musicListService.insertMusicList(musicList);
        } catch (Exception e) {
            // 捕获异常并打印错误信息
            e.printStackTrace();
            // 或者使用日志框架记录错误信息
            // logger.error("Failed to insert music list", e);
            return -1; // 或者根据实际情况返回适当的错误代码
        }
    }
//    @GetMapping
//    public List<MusicList> getAllMusicLists() {
//        return allMusicList;
//    }
}
