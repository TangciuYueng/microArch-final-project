package cn.edu.tongji.chat.service.impl;

import cn.edu.tongji.chat.repository.ChatPortRepository;
import cn.edu.tongji.chat.service.RunJSService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class RunJSServiceImpl implements RunJSService {
    @Resource
    private ChatPortRepository chatPortRepository;

    @Override
    public void run(String path, short port) {
        try {
            // 构建执行命令
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("node", path, String.valueOf(port));

            // 启动进程并等待执行完成
            Process process = processBuilder.start();

            Thread outputThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            outputThread.start();

            // 处理错误信息
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            int exitCode = process.waitFor();

//            // 读取输出信息
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // 处理错误信息
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String errorLine;
//            while ((errorLine = errorReader.readLine()) != null) {
//                System.err.println(errorLine);
//            }

            // 输出执行结果
            chatPortRepository.updateHostIdAndTypeAndGroupIdByPort(port, null, null, null);
            System.out.println("Node.js process exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
