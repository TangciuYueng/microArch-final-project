package cn.edu.tongji.login.service;

public interface SMSService {
    boolean sendSmsCode(String code, String phone);
}
