package cn.edu.tongji.diaryWriting.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreatedBy
    @Column(name = "user_id")
    private Integer userId;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDate createDate;

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "current_status")
    private Integer currentStatus;

    private String title;

    private String content;

    private String cover;

    @Column(name = "background_music")
    private String backgroundMusic;

    private String permission;
}
