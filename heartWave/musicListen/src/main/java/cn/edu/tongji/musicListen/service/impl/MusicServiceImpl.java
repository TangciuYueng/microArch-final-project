package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.service.MusicService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {
    @Resource
    private MusicMapper musicMapper;

    @Override
    public int insertMusic(Music music) {
        System.out.println(music);
        return musicMapper.insertMusic(music);
    }
    @Override
    public List<Music> getAllMusic(){
        return musicMapper.getAllMusic();
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
    public void playMusic(int id){

        // 通过音乐id获取音乐所在路径
        String filePath = "\\D:\\大学学习资料\\大三上学期学习\\微服务架构\\microArch-final-project\\heartWave\\musicListen\\abc.wav\\";

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
    }

}
