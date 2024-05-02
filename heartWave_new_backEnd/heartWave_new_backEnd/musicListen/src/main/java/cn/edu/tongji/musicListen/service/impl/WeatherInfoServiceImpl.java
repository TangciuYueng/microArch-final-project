package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.domain.WeatherInfo;
import cn.edu.tongji.musicListen.repository.MusicRepository;
import cn.edu.tongji.musicListen.repository.WeatherInfoRepository;
import cn.edu.tongji.musicListen.service.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherInfoServiceImpl implements WeatherInfoService {
    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Override
    public Optional<WeatherInfo> getWeatherInfoByCity(String city){
        return weatherInfoRepository.findByCity(city);
    }

}
