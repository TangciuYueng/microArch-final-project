package cn.edu.tongji.login.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 用户的唯一标识符，通常作为数据库主键。

    @Column(name = "name")
    private String name; // 用户的姓名。

    @Column(name = "password")
    private String password; // 用户账户的密码。

    @Column(name = "email")
    private String email; // 用户的电子邮箱地址。

    @Column(name = "createTime")
    private LocalDateTime createTime; // 账户创建的时间。

    @Column(name = "age")
    private Integer age; // 用户的年龄。

    @Column(name = "region")
    private String region; // 用户所在的地区或国家。

    @Column(name = "avatar")
    private String avatar; // 用户的头像图片链接。

    @Column(name = "phone")
    private String phone; // 用户的电话号码。

    @Column(name = "gender")
    private String gender; // 用户的性别。

    @Column(name = "image_list")
    private List<String> imageList; //用户的图片素材库

    @Column(name = "friend_count")
    private Integer friendCount; // 好友数量

    @Column(name = "mood_value")
    private Integer moodValue; // 心情值

    @Column(name = "visitor_count")
    private Integer visitorCount; // 主页访客数

    @Column(name = "diary_count")
    private Integer diaryCount; // 日记数量

    @Column(name = "playlist_count")
    private Integer playlistCount; // 歌单数量
}
