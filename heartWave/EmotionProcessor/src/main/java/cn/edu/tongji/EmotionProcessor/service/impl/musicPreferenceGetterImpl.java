package cn.edu.tongji.EmotionProcessor.service.impl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import cn.edu.tongji.EmotionProcessor.service.musicPreferenceGetter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class musicPreferenceGetterImpl implements musicPreferenceGetter {
    @Scheduled(fixedRate = 3600)
    public void runScript() {
        try {
            String argument ="";
            ProcessBuilder processBuilder = new ProcessBuilder("python", "EmotionProcessor/src/python/userPreference_getter.py", argument);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.println("Error: " + errorLine);
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
