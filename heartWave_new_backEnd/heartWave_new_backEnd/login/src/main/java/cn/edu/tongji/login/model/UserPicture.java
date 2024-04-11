package cn.edu.tongji.login.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_picture")
public class UserPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "url")
    private String url;
}
