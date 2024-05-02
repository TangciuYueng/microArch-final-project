package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.domain.Music;
import cn.edu.tongji.musicListen.domain.WeatherInfo;

import java.util.Optional;

public interface WeatherInfoService {
    Optional<WeatherInfo> getWeatherInfoByCity(String city);
}
