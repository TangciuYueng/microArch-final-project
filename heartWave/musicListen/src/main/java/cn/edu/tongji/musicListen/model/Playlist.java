package cn.edu.tongji.musicListen.model;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private int id;
    private int userId;
    private int musicId;
}
