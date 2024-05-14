package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.dto.*;
import cn.edu.tongji.login.model.GenderType;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.repository.UserRepository;
import cn.edu.tongji.login.service.COSService;
import cn.edu.tongji.login.service.EncryptService;
import cn.edu.tongji.login.service.SMSService;
import cn.edu.tongji.login.service.UserService;
import cn.edu.tongji.login.utils.ImageCompressor;
import cn.edu.tongji.login.utils.SmsCodeGenerator;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    private UserRepository userRepository;
    @Resource
    private EncryptService encryptService;
    @Resource
    private COSService cosService;
    @Resource
    private SMSService smsService;

    @Override
    @Transactional
    public User register(RegisterRequest registerRequest) throws IOException {
        User user = User.builder()
                .name(registerRequest.getName())
                .password(encryptService.encryptPassword(registerRequest.getPassword()))  //加密
                .createTime(LocalDateTime.now())  //当前时间
                .email(registerRequest.getEmail())
                .age(registerRequest.getAge())
                .region(registerRequest.getRegion())
                .phone(registerRequest.getPhone())
                .gender(GenderType.valueOf(registerRequest.getGender()))
                .diaryCount(0)
                .friendCount(0)
                .moodValue(0)
                .playlistCount(0)
                .visitorCount(0)
                .build();

        userRepository.save(user);
        final String fileName = user.getId() + ".jpg";  //文件名为用户id.jpg
        byte[] compressedImage = ImageCompressor.compress(
                Base64.getDecoder().decode(registerRequest.getAvatar()),
                128,
                128,
                true
        );
        cosService.uploadFileBytes(compressedImage, "userAvatar/" + fileName);
        userRepository.updateAvatarById(user.getId(), "userAvatar/" + user.getId() + ".jpg");

        return user;
    }

    @Override
    public boolean checkPhoneAvailable(String phone) {
        return userRepository.findAllByPhone(phone).isEmpty();
    }

    @Override
    public long checkPhoneUsed(String phone, String username) {
        List<User> users = userRepository.findAllByPhone(phone);

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

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        userRepository.updatePasswordById(
                resetPasswordRequest.getUserId(),
                encryptService.encryptPassword(resetPasswordRequest.getNewPassword())
        );
    }

    @Override
    public List<UserFriendInfo> getMultipleUsers(List<Long> ids) {
        List<UserFriendInfo> userFriendInfos = new ArrayList<>();

        for (long id : ids) {
            User user = userRepository.findFirstById(id);

            userFriendInfos.add(new UserFriendInfo(
                    user.getId(),
                    user.getName(),
                    user.getAvatar()
            ));
        }

        return userFriendInfos;
    }

    @Override
    public void updateFriendCount(UpdateFriendCountRequest updateFriendCountRequest) {
        int origin = userRepository.findFirstFriendCountById(updateFriendCountRequest.getUserId());
        int newFriendCount = origin + updateFriendCountRequest.getVariation();

        if (newFriendCount < 0)
            return;

        userRepository.updateFriendCountById(updateFriendCountRequest.getUserId(), newFriendCount);
    }
}
