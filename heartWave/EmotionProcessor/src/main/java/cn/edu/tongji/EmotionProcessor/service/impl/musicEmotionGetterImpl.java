package cn.edu.tongji.EmotionProcessor.service.impl;

import cn.edu.tongji.EmotionProcessor.service.musicEmotionGetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class musicEmotionGetterImpl implements musicEmotionGetter {
    @Scheduled(fixedRate = 36000)
    public void getMusicEmotion()
    {
        System.out.println("Starting music emotion getter...");
        try {
            String pythonScriptPath = "EmotionProcessor/src/python/MusicEmotionRecognition/music_emotion_recognition.py";
            String fileToAnalyze = "EmotionProcessor/src/python/MusicEmotionRecognition/MusicTest/sad.wav";
            String pythonVirtualEnvPath = "D:/anaconda3/envs/emotion/python.exe";
            ProcessBuilder processBuilder = new ProcessBuilder(pythonVirtualEnvPath, pythonScriptPath, fileToAnalyze);

            // Debug: print the command
            System.out.println("Executing command: " + processBuilder.command().stream().collect(Collectors.joining(" ")));

            Process process = processBuilder.start();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            StringBuilder errors = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errors.append(errorLine).append("\n");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error executing Python script:");
                System.err.println(errors.toString());
            }

            System.out.println("Python script output:");
            System.out.println(output.toString());
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
