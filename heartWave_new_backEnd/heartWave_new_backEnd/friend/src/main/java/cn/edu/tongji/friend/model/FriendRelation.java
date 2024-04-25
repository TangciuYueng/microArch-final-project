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
@Table(name = "friend_relation")
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id", nullable = false)
    private Friend friend;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "friend_id")
    private Long friendId;

    @Column(name = "intimacy", nullable = false)
    private Double intimacy;
}
