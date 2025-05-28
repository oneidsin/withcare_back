package profile.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import profile.dao.ProfileDAO;
import profile.dto.ProfileDTO;

@Service
public class ProfileService {

    @Autowired 
    ProfileDAO dao;
    
    @Value("${file.upload.directory}")
    private String uploadDir;
    
    Logger log = LoggerFactory.getLogger(getClass());

    // 프로필 보기
    public ProfileDTO getProfile(String id) {
        return dao.getProfileById(id);
    }

    // 프로필 수정
    public int updateProfile(ProfileDTO dto) {
        try {
            log.info("프로필 수정 서비스 시작 - ID: {}", dto.getId());
            
            // 기존 프로필 정보 조회
            ProfileDTO existingProfile = dao.getProfileById(dto.getId());
            if (existingProfile == null) {
                log.error("프로필 수정 실패 - 존재하지 않는 사용자: {}", dto.getId());
                return 0;
            }

            // 프로필 이미지 처리
            if (dto.getProfile_photo() == null || dto.getProfile_photo().trim().isEmpty()) {
                dto.setProfile_photo(existingProfile.getProfile_photo());
            }

            // null 값 처리
            if (dto.getCancer_idx() == null) {
                dto.setCancer_idx(existingProfile.getCancer_idx());
            }
            if (dto.getStage_idx() == null) {
                dto.setStage_idx(existingProfile.getStage_idx());
            }

            // 프로필 수정 실행
            int result = dao.updateProfile(dto);
            log.info("프로필 수정 완료 - ID: {}, 결과: {}", dto.getId(), result);
            
            return result;
        } catch (Exception e) {
            log.error("프로필 수정 중 오류 발생 - ID: {}, 에러: {}", dto.getId(), e.getMessage(), e);
            throw new RuntimeException("프로필 수정 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    public String saveProfileImage(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 파일 확장자 검사
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!extension.matches("jpg|jpeg|png|gif")) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }

        // 업로드 디렉토리 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일명 생성
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        Path targetPath = Paths.get(uploadDir, newFileName);

        try {
            // 파일 저장
            Files.copy(file.getInputStream(), targetPath);
            log.info("프로필 이미지 저장 완료: {}", targetPath);
            
            // 파일 URL 반환
            return "/uploads/" + newFileName;
        } catch (Exception e) {
            log.error("파일 저장 중 오류 발생", e);
            throw new Exception("파일 저장에 실패했습니다.");
        }
    }
} 