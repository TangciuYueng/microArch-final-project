package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.service.EncryptService;
import jakarta.annotation.Resource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor; //导入了Jasypt库中的PBE（Password Based Encryption）字符串加密器。
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EncryptServiceImpl implements EncryptService {
    @Resource
    private StandardPBEStringEncryptor encryptor;

    @Override
    public String encryptPassword(String password) {
        return encryptor.encrypt(password);
    }

    @Override
    public boolean passwordCmp(String encrypted, String origin) {
        return Objects.equals(encryptor.decrypt(encrypted), origin);
    }
}
