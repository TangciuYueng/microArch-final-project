package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.dto.MusicInfo;
import cn.edu.tongji.musicListen.dto.MusicRoomSongRequest;
import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.MusicService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {
    private static final int PAGE_SIZE = 30;
    @Resource
    private MusicListMapper musicListMapper;
    @Resource
    private MusicMapper musicMapper;

    @Override
    public int insertMusic(Music music) {
        System.out.println(music);
        return musicMapper.insertMusic(music);
    }
    @Override
    public MusicInfo getAllMusic(int page) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<Music> musics = musicMapper.getMusicByPage(startIndex, PAGE_SIZE);
        int musicCount = musicMapper.getAllMusicCount();
        int totalPage = (int) Math.ceil((double) musicCount / PAGE_SIZE);

        return MusicInfo.builder()
                .musics(musics)
                .page(page)
                .totalPage(totalPage)
                .build();
    }
    @Override
    public List<Music> getNewMusicToday(){
        return musicMapper.getNewMusicToday();
    }
    @Override
    public void updateMusic(Music music){
        musicMapper.updateMusic(music);
    }

    @Override
    public void deleteMusic(int id) {
        musicMapper.deleteMusic(id);
    }

    // 通过音乐id获取音乐
    @Override
    public Music getMusicById(int id){
        return musicMapper.selectMusicById(id);
    }
    //获取所有音乐的数量
    @Override
    public int getAllMusicCount(){
        return musicMapper.getAllMusicCount();
    }

    // 播放音乐
    @Override
    public int playMusic(int id){
        // 通过音乐id获取音乐所在路径
        System.out.println("play music!");
        Music music = getMusicById(id);

        String filePath = music.getSrc();

//        String filePath = "D:\\大学学习资料\\大三上学期学习\\微服务架构\\microArch-final-project\\heartWave\\musicListen\\abc.wav";

        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[4096];
            int bytesRead = 0;

            while ((bytesRead = audioStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        return 1;
    }


    // 增加音乐室唱歌记录
    @Override
    public void addMusicRoomSong(MusicRoomSongRequest request) {
        // 构建 Music
        Music music = Music.builder()
                .type(request.getType())
                .src(request.getSrc())
                .language(request.getLanguage())
                .build();
        // 插入 music 表
        int newId = musicMapper.insertMusic(music);
        // 加入 music_list 表
        MusicList musicList = MusicList.builder()
                .musicId(newId)
                .type("personal")
                .userId(request.getUserId())
                .build();
        musicListMapper.insertMusicList(musicList);
    }

}
