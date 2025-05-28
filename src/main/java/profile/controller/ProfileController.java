package profile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import profile.dto.ProfileDTO;
import profile.service.ProfileService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", 
    allowedHeaders = "*", 
    exposedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService svc;

    Logger log = LoggerFactory.getLogger(getClass());

    // 프로필 수정
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProfile(@RequestBody ProfileDTO dto) {
        try {
            log.info("프로필 수정 요청 시작");
            log.info("요청 데이터: id={}, name={}, email={}, cancer_idx={}, stage_idx={}, profile_yn={}", 
                    dto.getId(), dto.getName(), dto.getEmail(), 
                    dto.getCancer_idx(), dto.getStage_idx(), dto.isProfile_yn());

            // 필수 필드 검증
            if (dto.getId() == null || dto.getId().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("status", "error", "message", "ID는 필수 항목입니다."));
            }

            int result = svc.updateProfile(dto);
            log.info("프로필 수정 결과: {}", result);
            
            if (result > 0) {
                return ResponseEntity.ok(Map.of("status", "success", "message", "프로필이 성공적으로 수정되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("status", "error", "message", "프로필 수정에 실패했습니다."));
            }
        } catch (Exception e) {
            log.error("프로필 수정 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "서버 오류가 발생했습니다.",
                        "error", e.getMessage()
                    ));
        }
    }

    // 프로필 이미지 업로드
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            log.info("프로필 이미지 업로드 요청 - 파일 크기: {}", file.getSize());
            String imageUrl = svc.saveProfileImage(file);
            return ResponseEntity.ok(Map.of("status", "success", "url", imageUrl));
        } catch (IllegalArgumentException e) {
            log.error("프로필 이미지 업로드 중 유효성 검사 실패", e);
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            log.error("프로필 이미지 업로드 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "이미지 업로드에 실패했습니다."));
        }
    }

    // 프로필 열람
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") String id) {
        try {
            ProfileDTO profile = svc.getProfile(id);
            if (profile != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", profile);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("status", "error", "message", "프로필을 찾을 수 없습니다."));
            }
        } catch (Exception e) {
            log.error("프로필 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "서버 오류가 발생했습니다."));
        }
    }
} 