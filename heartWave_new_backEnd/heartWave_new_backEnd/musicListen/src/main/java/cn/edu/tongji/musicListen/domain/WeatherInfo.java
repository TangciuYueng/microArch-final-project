package cn.edu.tongji.musicListen.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather_info")
public class WeatherInfo {

    @Id
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "weather_condition")
    private String weatherCondition;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "api_response_time")
    private LocalDateTime apiResponseTime;
}
