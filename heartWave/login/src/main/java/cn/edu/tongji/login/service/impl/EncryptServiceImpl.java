package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.service.EncryptService;
import jakarta.annotation.Resource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EncryptServiceImpl implements EncryptService {
//    @Resource
//    private StandardPBEStringEncryptor encryptor;


    public EncryptServiceImpl() {
//        encryptor.setAlgorithm();
//        encryptor.setPassword();
    }

    @Override
    public String encryptPassword(String password) {
        //return encryptor.encrypt(password);
        return "";
    }

    @Override
    public boolean passwordCmp(String encrypted, String origin) {
        //return Objects.equals(encryptor.decrypt(encrypted), origin);
        return true;
    }
}
