package com.withcare;

import com.withcare.util.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@EnableScheduling
@SpringBootApplication
public class WithCareApplication {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public static void main(String[] args) {
        // 현재 실행 위치 출력
        System.out.println("Application running directory: " + System.getProperty("user.dir"));
        
        SpringApplication.run(WithCareApplication.class, args);

        if (JwtToken.JwtUtils.getPri_key() == null) {
            JwtToken.JwtUtils.setPri_key();
        }
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("Upload directory configuration: " + uploadDir);
            
            // 업로드 기본 디렉토리 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created upload directory at: " + uploadPath.toAbsolutePath());
            } else {
                System.out.println("Using existing upload directory at: " + uploadPath.toAbsolutePath());
            }

            // 레벨 아이콘을 위한 하위 디렉토리 생성
            Path levelPath = uploadPath.resolve("level");
            if (!Files.exists(levelPath)) {
                Files.createDirectories(levelPath);
                System.out.println("Created level directory at: " + levelPath.toAbsolutePath());
            }

            // 기타 필요한 하위 디렉토리들 생성
            String[] subDirs = {"profile", "post", "timeline"};
            for (String dir : subDirs) {
                Path subPath = uploadPath.resolve(dir);
                if (!Files.exists(subPath)) {
                    Files.createDirectories(subPath);
                    System.out.println("Created " + dir + " directory at: " + subPath.toAbsolutePath());
                }
            }
        };
    }
}
