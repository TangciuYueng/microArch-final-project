package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.dto.COSFileRequest;
import cn.edu.tongji.musicListen.dto.MusicInfo;
import cn.edu.tongji.musicListen.dto.MusicRoomSongRequest;
import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.COSService;
import cn.edu.tongji.musicListen.service.MusicService;
import jakarta.annotation.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {
    private static final int PAGE_SIZE = 30;
    @Resource
    private MusicListMapper musicListMapper;
    @Resource
    private MusicMapper musicMapper;

    @Resource
    private COSService cosService;

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
        System.out.println("This music is going to play: " + music);
        // music里有oss的路径(src)
        // 从oss里下载到一个固定的路径
        COSFileRequest cosFileRequest = COSFileRequest.builder()
                .cosPath(music.getSrc())
                .localPath("./musicListen/backend_music/" + music.getTitle())
                .build();
        System.out.println(cosFileRequest.getLocalPath());
        cosService.downloadFile(cosFileRequest);
        

        String filePath = cosFileRequest.getLocalPath();

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

    private static final String AUDIO_DIRECTORY = "/music_listen/backend_music"; // 替换成实际的音频文件目录

    @Override
    public ResponseEntity<?> downloadAudio(String filePath) throws IOException {

        // 构建音频文件的完整路径
//        String fullFilePath = AUDIO_DIRECTORY + filePath;
        String fullFilePath = "D:\\大学学习资料\\大三上学期学习\\微服务架构\\microArch-final-project\\heartWave\\musicListen\\backend_music\\abc.wav";
        System.out.println(fullFilePath);

        String currentDirectory = System.getProperty("user.dir");

        // 打印当前工作目录
        System.out.println("Current Directory: " + currentDirectory);

        Path path = Paths.get(fullFilePath);

        // 构建响应实体
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getFileName(filePath));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Files.size(path))
                .body(resource);
    }

    private String getFileName(String filePath) {
        // 从文件路径中提取文件名
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

}
