package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.config.AliyunSmsConfig;
import cn.edu.tongji.login.service.SMSService;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(AliyunSmsConfig.class)
public class SMSServiceImpl implements SMSService {
    @Resource
    private AliyunSmsConfig aliyunSmsConfig;
    @Resource
    private IAcsClient client;

    @Override
    public boolean sendSmsCode(String code, String phone) {
        // 构造短信请求对象
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(aliyunSmsConfig.getSignName());
        request.setTemplateCode(aliyunSmsConfig.getTemplateCode());
        request.setTemplateParam("{\"code\":\"" + code + "\"}"); // 替换为实际的模板参数

        try {
            // 发送短信并获取响应
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println("短信发送成功，请求ID：" + response.getRequestId());
            return true;
        } catch (ClientException e) {
            System.out.println("短信发送失败：" + e.getErrMsg());
            return false;
        }
    }
}
