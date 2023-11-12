package cn.edu.tongji.musicroom.model;

public class ChatRecord {
    private int id;
    private String content;
    private Type type;
    private int userId;
    private int musicRoomId;
    private boolean isDeleted;

    public enum Type {
        TEXT,
        URL,
        PIC
    }
}
