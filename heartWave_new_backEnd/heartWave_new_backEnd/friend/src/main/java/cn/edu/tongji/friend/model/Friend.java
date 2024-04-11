package cn.edu.tongji.friend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long user;

    @Column(name = "friendId", nullable = false)
    private Long friendId;

    @Column(name = "intimacy", nullable = false)
    private Double intimacy;
}
