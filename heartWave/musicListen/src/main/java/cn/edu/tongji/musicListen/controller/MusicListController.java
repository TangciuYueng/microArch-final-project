package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.MusicListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/musiclisten")
@RequiredArgsConstructor
public class MusicListController {
    private final MusicListService musicListService;
    @PostMapping("/musiclist")
    public int insertMusicList(MusicList musicList) {
        try {
            return musicListService.insertMusicList(musicList);
        } catch (Exception e) {
            // 捕获异常并打印错误信息
            e.printStackTrace();
            // 或者使用日志框架记录错误信息
            // logger.error("Failed to insert music list", e);
            return -1; // 或者根据实际情况返回适当的错误代码
        }
    }

}
