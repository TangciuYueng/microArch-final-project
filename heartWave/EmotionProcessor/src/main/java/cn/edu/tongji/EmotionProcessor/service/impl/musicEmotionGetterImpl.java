package cn.edu.tongji.EmotionProcessor.service.impl;


import cn.edu.tongji.EmotionProcessor.service.musicEmotionGetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class musicEmotionGetterImpl implements musicEmotionGetter {
    @Scheduled(fixedRate = 36000000)
    public void getMusicEmotion()
    {
        System.out.println(1);
        try {
            String pythonScriptPath = "EmotionProcessor/src/python/MusicEmotionRecognition/music_emotion_recognition.py";
            String fileToAnalyze = "EmotionProcessor/src/python/MusicEmotionRecognition/MusicTest/Hoàng Thùy Linh - Gieo Quẻ.mp3";
            String pythonVirtualEnvPath = "D:/anaconda3/envs/emotion/python.exe";
            ProcessBuilder processBuilder = new ProcessBuilder(pythonVirtualEnvPath, pythonScriptPath, fileToAnalyze);
            Process process = processBuilder.start();
            System.out.println("starting completed");
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Handle error
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
