package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.mapper.PlaylistMapper;
import cn.edu.tongji.musicListen.model.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PlaylistService {
    List<Playlist> getAllPlaylist();

    int insertPlaylist(Playlist playlist);

    void deletePlaylist(int id);

    void updatePlaylist(Playlist playlist);
}
