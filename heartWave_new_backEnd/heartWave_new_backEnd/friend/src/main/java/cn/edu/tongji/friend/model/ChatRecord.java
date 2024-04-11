package cn.edu.tongji.friend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_record")
public class ChatRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatRecordType type;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
