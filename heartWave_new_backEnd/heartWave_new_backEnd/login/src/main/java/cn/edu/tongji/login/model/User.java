package cn.edu.tongji.login.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor //
@Entity             //这个注解指示这是一个JPA实体类，将被持久化到数据库中。
@Table(name = "user")//这个注解指定了实体类对应的数据库表名为"user"
public class User {
    @Id                 //这个注解指示id属性为数据库表的主键。
    @GeneratedValue(strategy = GenerationType.IDENTITY)//这个注解指示数据库为每个新实体对象分配一个唯一标识符，并使用自增策略。
    private Long id;  // 用户的唯一标识符，通常作为数据库主键。

    @Column(name = "name", nullable = false) //这个注解指定了实体属性与数据库表中的列的映射关系。
    private String name; // 用户的姓名。

    @Column(name = "password", nullable = false)
    private String password; // 用户账户的密码。

    @Column(name = "email")
    private String email; // 用户的电子邮箱地址。

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime; // 账户创建的时间。

    @Column(name = "age")
    private Integer age; // 用户的年龄。

    @Column(name = "region")
    private String region; // 用户所在的地区或国家。

    @Column(name = "avatar")
    private String avatar; // 用户的头像图片链接。

    @Column(name = "phone", nullable = false)
    private String phone; // 用户的电话号码。

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderType gender; // 用户的性别。

    @Column(name = "image_list")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPicture> imageList; //用户的图片素材库

    @Column(name = "friend_count", nullable = false)
    private Integer friendCount; // 好友数量

    @Column(name = "mood_value", nullable = false)
    private Integer moodValue; // 心情值

    @Column(name = "visitor_count", nullable = false)
    private Integer visitorCount; // 主页访客数

    @Column(name = "diary_count", nullable = false)
    private Integer diaryCount; // 日记数量

    @Column(name = "playlist_count", nullable = false)
    private Integer playlistCount; // 歌单数量
}
