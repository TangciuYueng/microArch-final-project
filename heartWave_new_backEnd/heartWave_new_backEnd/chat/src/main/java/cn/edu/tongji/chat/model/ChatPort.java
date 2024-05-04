package cn.edu.tongji.chat.model;

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
@Table(name = "chat-port")
public class ChatPort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "port", nullable = false)
    private Short port;

    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChatPortType type;

    @Column(name = "group_id")
    private Long groupId;
}
