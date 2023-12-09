package cn.edu.tongji.Emotions.interfaces;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "music-listen-service", path = "/api/music_listen/music")
public interface MusicServiceClient {

    @GetMapping("/{id}")
    ResponseEntity<?> getMusicById(@PathVariable("id") int id);
}
