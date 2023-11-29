package cn.edu.tongji.Emotions.interfaces;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "music-listen-service", path = "/api/musiclisten/music")
public class MusicServiceClient {
}
