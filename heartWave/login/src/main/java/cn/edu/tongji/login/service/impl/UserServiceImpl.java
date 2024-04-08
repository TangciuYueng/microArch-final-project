package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.dto.*;
import cn.edu.tongji.login.mapper.UserMapper;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.service.COSService;
import cn.edu.tongji.login.service.EncryptService;
import cn.edu.tongji.login.service.SmsService;
import cn.edu.tongji.login.service.UserService;
import cn.edu.tongji.login.utils.SmsCodeGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private EncryptService encryptService;
    @Resource
    private SmsService smsService;
    @Resource
    private COSService cosService;

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAll();
    }

    @Override
    public List<UserFriendInfo> getUsersByIds(List<Integer> ids) {
        List<UserFriendInfo> userFriendInfos = new ArrayList<>();

        for (int id : ids) {
            User user = userMapper.getById(id);

            userFriendInfos.add(new UserFriendInfo(
                    user.getId(),
                    user.getName(),
                    user.getAvatar()
            ));
        }

        return userFriendInfos;
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getById(id);
    }

    @Override
    public UserInfo getUserInfoById(int id) {
        User user = userMapper.getById(id);

        return new UserInfo(
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getRegion(),
                user.getAvatar(),
                user.getPhone(),
                user.getGender()
        );
    }

    @Override
    @Transactional
    public User addUser(AddUserRequest addUserRequest) throws IOException {
        User user = User.builder()
                .name(addUserRequest.getName())
                .password(encryptService.encryptPassword(addUserRequest.getPassword()))  //加密
                .createTime(LocalDateTime.now())  //当前时间
                .email(addUserRequest.getEmail())
                .age(addUserRequest.getAge())
                .region(addUserRequest.getRegion())
                .phone(addUserRequest.getPhone())
                .gender(addUserRequest.getGender())
                .build();

        userMapper.add(user);
        final String fileName = user.getId() + ".jpg";  //文件名为用户id.jpg

        byte[] tmpBytes = Base64.getDecoder().decode(addUserRequest.getAvatar());  //将base64解码为byte[]
        File tmpFile = new File("./" + fileName);

        if (!tmpFile.exists() && !tmpFile.createNewFile()) {  //创建文件
            return null;
        }

        try (FileOutputStream out = new FileOutputStream(tmpFile)) {  //写入文件
            out.write(tmpBytes);
        }

        cosService.uploadFile(new COSFileRequest("./" + fileName, "userAvatar/" + fileName));  //上传到文件服务器
        userMapper.update(User.builder()
                .id(user.getId())
                .avatar("userAvatar/" + user.getId() + ".jpg")  //添加路径
                .build()
        );

        if (tmpFile.exists() && !tmpFile.delete()) {  //删除文件
            return null;
        }

        return userMapper.getById(user.getId());
    }

    @Override
    public void updateUser(UpdateUserRequest updateUserRequest) {
        userMapper.update(User.builder()
                .id(updateUserRequest.getId())
                .name(updateUserRequest.getName())
                .password(encryptService.encryptPassword(updateUserRequest.getPassword()))
                .email(updateUserRequest.getEmail())
                .age(updateUserRequest.getAge())
                .region(updateUserRequest.getRegion())
                .avatar(updateUserRequest.getAvatar())
                .phone(updateUserRequest.getPhone())
                .gender(updateUserRequest.getGender())
                .build()
        );
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        userMapper.update(User.builder()
                .id(resetPasswordRequest.getUserId())
                .password(encryptService.encryptPassword(resetPasswordRequest.getNewPassword()))
                .build()
        );
    }

    @Override
    public boolean checkPhoneAvailable(String phone) {
        return userMapper.getByPhone(phone).isEmpty();
    }

    @Override
    public int checkPhoneUsed(String phone, String username) {
        List<User> users = userMapper.getByPhone(phone);

        if (!users.isEmpty() && Objects.equals(users.get(0).getName(), username)) {
            return users.get(0).getId();
        }
        else {
            return -1;
        }
    }

    @Override
    public SmsInfo sendSmsCode(String phone) {
        String code = SmsCodeGenerator.getCode();
        boolean ifSend = smsService.sendSmsCode(code, phone);
        return new SmsInfo(ifSend ? code : null, ifSend);
    }
}
