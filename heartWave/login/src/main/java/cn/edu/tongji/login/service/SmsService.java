package cn.edu.tongji.login.service;

import org.springframework.stereotype.Service;

@Service
public interface SmsService {
    boolean sendSmsCode(String code, String phone);
}
