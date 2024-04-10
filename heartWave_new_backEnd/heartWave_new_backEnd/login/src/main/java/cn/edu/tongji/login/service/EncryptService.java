package cn.edu.tongji.login.service;

public interface EncryptService {
    String encryptPassword(String password);
    boolean passwordCmp(String encrypted, String origin);
}
