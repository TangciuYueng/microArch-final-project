package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Integer>, JpaSpecificationExecutor<WeatherInfo> {
    Optional<WeatherInfo> findByCity(String city);
}
