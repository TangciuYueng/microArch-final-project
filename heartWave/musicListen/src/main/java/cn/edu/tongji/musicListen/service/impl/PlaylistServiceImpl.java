package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.mapper.PlaylistMapper;
import cn.edu.tongji.musicListen.model.Playlist;
import cn.edu.tongji.musicListen.service.PlaylistService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlaylistServiceImpl implements PlaylistService {
    @Resource
    private PlaylistMapper playlistMapper;
    @Override
    public List<Playlist> getAllPlaylist() {
        return playlistMapper.getAllPlaylist();
    }
    @Override
    public int insertPlaylist(Playlist playlist) {
        return playlistMapper.insertPlaylist(playlist);
    }
    @Override
    public void deletePlaylist(int id) {
        playlistMapper.deletePlaylist(id);
    }
    @Override
    public void updatePlaylist(Playlist playlist){
        playlistMapper.updatePlaylist(playlist);
    }
}
