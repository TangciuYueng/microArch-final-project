package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.common.Result;
import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.domain.WeatherInfo;
import cn.edu.tongji.musicListen.service.WeatherInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/music-listen/weather-info")
public class WeatherInfoController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherInfoController.class);
    @Autowired
    private WeatherInfoService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<Result<?>> getWeatherInfoByCity(@PathVariable("city") String city) {
        try {
            if (Objects.equals(city, "")) {
                return ResponseEntity.badRequest().body(new Result<>(400, "city不能为空", null));
            }

            Optional<WeatherInfo> optionalWeatherInfo = weatherService.getWeatherInfoByCity(city);
            return optionalWeatherInfo.<ResponseEntity<Result<?>>>map(WeatherInfo -> ResponseEntity.ok(new Result<>(200, "成功", WeatherInfo))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(404, "未找到city为 " + city + " 的天气信息", null)));
        } catch (Exception e) {
            logger.error("获取天气信息失败，city：" + city, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取音乐列表信息失败", null));
        }
    }
}
