package com.withcare.admin.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.admin.dto.AdminMemberDTO;
import com.withcare.admin.dto.AdminMemberDetailDTO;
import com.withcare.admin.service.AdminService;
import com.withcare.member.dto.MemberDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.BadgeDTO;
import com.withcare.profile.dto.CancerDTO;
import com.withcare.profile.dto.LevelDTO;
import com.withcare.profile.dto.StageDTO;
import com.withcare.profile.dto.TimelineDTO;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class AdminController {

	@Autowired
	AdminService svc;

	Logger log = LoggerFactory.getLogger(getClass());

	Map<String, Object> result = null;

	@Value("${file.upload-dir}")
	private String baseUploadPath;

	@Value("${upload.url-prefix}")
	private String urlPrefix;

	// 관리자 권한 부여
	@PutMapping("/admin/grant")
	public Map<String, Object> adminGrant(@RequestBody MemberDTO memberDTO, @RequestHeader Map<String, String> header) {

		result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		boolean login = false;
		boolean success = false;

		// 로그인 사용자 레벨 확인
		int user_lv = svc.userLevel(loginId);

		// 관리자 레벨 체크
		if (user_lv != 7) {
			result.put("success", false);
			return result;
		}

		// 로그인 유효성 체크
		if (loginId != null && !loginId.isEmpty()) {
			login = true;

			// 권한 부여 실행
			params.put("id", memberDTO.getId());
			params.put("lv_idx", memberDTO.getLv_idx());

			success = svc.levelUpdate(params);
		}

		// 결과 반환
		result.put("success", success);
		result.put("loginYN", login);

		return result;
	}

	// 멤버 리스트 확인
	@PostMapping("/admin/member/list")
	public Map<String, Object> adminMemberList(@RequestBody(required = false) Map<String, Object> params,
			@RequestHeader Map<String, String> header) {

		result = new HashMap<String, Object>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		boolean login = false;

		// 로그인 유효성 체크
		if (loginId == null || loginId.isEmpty()) {
			result.put("success", false);
			login = true;
			return result;
		}

		// 로그인 사용자 레벨 확인
		int user_lv = svc.userLevel(loginId);

		// 관리자 레벨 체크
		if (user_lv != 7) {
			result.put("success", false);
			return result;
		}

		// null 이면 가져오지 말아라
		String searchId = (params != null) ? (String) params.get("searchId") : null;
		String sortField = (params != null) ? (String) params.get("sortField") : null;
		String sortOrder = (params != null) ? (String) params.get("sortOrder") : null;
		String blockFilter = (params != null) ? (String) params.get("blockFilter") : null;
		String adminFilter = (params != null) ? (String) params.get("adminFilter") : null;
		String delFilter = (params != null) ? (String) params.get("delFilter") : null;

		Boolean adminYN = null;
		Boolean delYN = null;
		Boolean blockYN = null;

		// page, size 초기화
		int page = 1;
		int size = 10;

		// null 아닐때만 가져와라
		if (params != null && params.get("page") != null) {
			page = Integer.parseInt(params.get("page").toString());
		}

		if (params != null && params.get("size") != null) {
			size = Integer.parseInt(params.get("size").toString());
		}

		if ("Y".equalsIgnoreCase(blockFilter))
			blockYN = true;
		else if ("N".equalsIgnoreCase(blockFilter))
			blockYN = false;

		// start 초기화
		int start = (page - 1) * size;

		if ("Y".equalsIgnoreCase(adminFilter))
			adminYN = true;
		else if ("N".equalsIgnoreCase(adminFilter))
			adminYN = false;

		if ("Y".equalsIgnoreCase(delFilter))
			delYN = true;
		else if ("N".equalsIgnoreCase(delFilter))
			delYN = false;

		result.put("searchId", searchId);
		result.put("start", start);
		result.put("size", size);
		result.put("sortField", sortField);
		result.put("sortOrder", sortOrder);
		result.put("blockFilter", blockFilter);
		result.put("adminFilter", adminFilter);
		result.put("delFilter", delFilter);
		result.put("adminYN", adminYN);
		result.put("delYN", delYN);
		result.put("blockYN", blockYN);

		List<AdminMemberDTO> memberList = svc.adminMemberList(result);

		result.put("success", true);
		result.put("data", memberList);
		result.put("loginId", loginId);

		int totalPosts = svc.adminMemberCnt(result); // 게시글 개수 조회용 쿼리 새로 추가
		int totalPages = (int) Math.ceil((double) totalPosts / size);

		result.put("totalPosts", totalPosts);
		result.put("totalPages", totalPages);

		return result;
	}

	// 회원 정보 상세보기
	@PostMapping("/admin/member/detail")
	public Map<String, Object> adminMemberDetail(@RequestBody Map<String, String> param,
			@RequestHeader Map<String, String> header) {

		Map<String, Object> result = new HashMap<String, Object>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			return result;
		}

		String targetId = param.get("id");

		AdminMemberDetailDTO detail = svc.adminMemberDetail(targetId);

		result.put("success", true);
		result.put("data", detail);
		return result;
	}

	// 작성 게시글 목록 확인
	@GetMapping("/admin/member/{id}/posts")
	public Map<String, Object> adminMemberPosts(@PathVariable String id, @RequestHeader Map<String, String> header) {

		Map<String, Object> result = new HashMap<String, Object>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			return result;
		}

		List<PostDTO> posts = svc.adminMemberPost(id);

		result.put("success", true);
		result.put("data", posts);
		return result;
	}

	// 작성 댓글 목록 확인
	@GetMapping("/admin/member/{id}/comments")
	public Map<String, Object> adminMemberComments(@PathVariable String id, @RequestHeader Map<String, String> header) {

		Map<String, Object> result = new HashMap<String, Object>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			return result;
		}

		List<Map<String, Object>> comments = svc.adminMemberComments(id);

		result.put("success", true);
		result.put("data", comments);
		return result;
	}

	// 타임라인 목록 확인
	@GetMapping("/admin/member/{id}/timelines")
	public Map<String, Object> adminMemberTimelines(@PathVariable String id,
			@RequestHeader Map<String, String> header) {

		Map<String, Object> result = new HashMap<String, Object>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			return result;
		}

		List<TimelineDTO> timelines = svc.adminMemberTimeline(id);

		result.put("success", true);
		result.put("data", timelines);
		return result;
	}

	// 추천 누른 게시글 (비추천 X)
	@PostMapping("/admin/member/like")
	public Map<String, Object> adminMemberLike(@RequestBody Map<String, String> param,
			@RequestHeader Map<String, String> header) {

		result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			return result;
		}

		String targetId = param.get("id");

		List<LikeDislikeDTO> likes = svc.adminMemberLike(targetId);
		result.put("success", true);
		result.put("data", likes);

		return result;
	}

	// 타임라인 확인
	@PostMapping("/admin/member/timelines")
	public Map<String, Object> adminMemberTimeline(@RequestBody Map<String, String> param,
			@RequestHeader Map<String, String> header) {

		result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			return result;
		}

		String targetId = param.get("id");

		List<TimelineDTO> timeline = svc.adminMemberTimeline(targetId);
		result.put("success", true);
		result.put("data", timeline);

		return result;
	}

	// 공통 파일 저장 함수
	private String saveFile(MultipartFile file, String type) throws Exception {
		String original = file.getOriginalFilename();
		if (original == null || !original.toLowerCase().matches(".*\\.(png|jpg|jpeg|webp)$")) {
			throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
		}
		String ext = original.substring(original.lastIndexOf(".")).toLowerCase();
		String fileName = UUID.randomUUID() + ext;
		Path saveDir = Paths.get(baseUploadPath, type);
		Files.createDirectories(saveDir);
		Path savePath = saveDir.resolve(fileName);
		Files.write(savePath, file.getBytes());
		return urlPrefix + "/" + type + "/" + fileName;
	}

	// 배지 목록 조회
	@GetMapping("/admin/bdg/list")
	public Map<String, Object> getBadgeList(@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = null;
		try {
			loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			// 토큰 파싱 오류 또는 토큰 부재 시 처리
			result.put("success", false);
			result.put("msg", "인증 토큰이 유효하지 않습니다.");
			return result;
		}

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			List<BadgeDTO> badgeList = svc.adminBdgList();
			result.put("success", true);
			result.put("badges", badgeList);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "배지 목록을 불러오는데 실패했습니다: " + e.getMessage());
		}
		return result;
	}

	// 배지 통합 등록/수정
	@PostMapping("/admin/bdg/save")
	public Map<String, Object> saveBadge(@RequestParam(value = "bdg_idx", required = false) Integer bdgIdx,
	        @RequestParam(value = "file", required = false) MultipartFile file, // required = false로 변경
	        @RequestParam("bdg_name") String bdgName,
	        @RequestParam("bdg_condition") String bdgCondition, 
	        @RequestParam("bdg_active_yn") boolean bdgActiveYn,
	        @RequestHeader Map<String, String> header) {

	    result = new HashMap<>();
	    String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	    if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
	        result.put("success", false);
	        result.put("msg", "관리자 권한이 필요합니다.");
	        return result;
	    }
	    
	    try {
	        BadgeDTO dto = new BadgeDTO();
	        dto.setBdg_name(bdgName);
	        dto.setBdg_condition(bdgCondition);
	        dto.setBdg_active_yn(bdgActiveYn);
	        
	        String url = null;
	        
	        // 파일이 있고 비어있지 않을 때만 파일 저장
	        if (file != null && !file.isEmpty()) {
	            url = saveFile(file, "badge");
	            dto.setBdg_icon(url);
	        } else if (bdgIdx == null) {
	            // 새 배지 등록인데 파일이 없으면 에러
	            result.put("success", false);
	            result.put("msg", "새 배지 등록 시 이미지 파일이 필요합니다.");
	            return result;
	        }
	        // 수정 시 파일이 없으면 기존 이미지 유지 (setBdg_icon 호출하지 않음)
	        
	        boolean success;
	        if (bdgIdx != null) {
	            dto.setBdg_idx(bdgIdx);
	            success = svc.adminBdgUpdate(dto);
	        } else {
	            success = svc.adminBdgAdd(dto);
	        }
	        
	        result.put("success", success);
	        if (url != null) {
	            result.put("url", url);
	        }
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("msg", e.getMessage());
	    }
	    return result;
	}

	// 배지 삭제
	@PutMapping("/admin/bdg/delete")
	public Map<String, Object> deleteBadge(@RequestBody Map<String, Object> param,
			@RequestHeader Map<String, String> header) {
		int bdgIdx = (int) param.get("bdg_idx");
		result = new HashMap<>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		BadgeDTO dto = new BadgeDTO();
		dto.setBdg_idx(bdgIdx);
		dto.setBdg_active_yn(false);

		boolean success = svc.adminBdgDelete(dto);
		result.put("success", success);
		return result;
	}

	// 레벨 리스트
	@GetMapping("/admin/level")
	public ResponseEntity<List<LevelDTO>> levelList() {
		List<LevelDTO> levels = svc.levelList();
		return ResponseEntity.ok(levels);
	}

	// 레벨 통합 등록/수정
	@PostMapping("/admin/level/save")
	public Map<String, Object> saveLevel(@RequestParam(value = "lv_idx", required = false) Integer lvIdx,
			@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("lv_no") int lvNo,
			@RequestParam("lv_name") String lvName, @RequestParam("post_cnt") int postCnt,
			@RequestParam("com_cnt") int comCnt, @RequestParam("like_cnt") int likeCnt,
			@RequestParam("time_cnt") int timeCnt, @RequestParam("access_cnt") int accessCnt,
			@RequestHeader Map<String, String> header) {

		result = new HashMap<>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}
		try {
			LevelDTO dto = new LevelDTO();
			dto.setLv_no(lvNo);
			dto.setLv_name(lvName);
			dto.setPost_cnt(postCnt);
			dto.setCom_cnt(comCnt);
			dto.setLike_cnt(likeCnt);
			dto.setTime_cnt(timeCnt);
			dto.setAccess_cnt(accessCnt);

			// 파일이 있을 경우에만 저장
			if (file != null && !file.isEmpty()) {
				String url = saveFile(file, "level"); // level 폴더에 저장
				dto.setLv_icon(url);
			}

			boolean success;
			if (lvIdx != null) {
				dto.setLv_idx(lvIdx);
				// 파일이 없는 경우 기존 아이콘 유지
				if (file == null || file.isEmpty()) {
					LevelDTO existingLevel = svc.getLevelById(lvIdx);
					if (existingLevel != null) {
						dto.setLv_icon(existingLevel.getLv_icon());
					}
				}
				success = svc.adminLevelUpdate(dto);
			} else {
				if (file == null || file.isEmpty()) {
					result.put("success", false);
					result.put("msg", "새 레벨 추가 시에는 아이콘이 필수입니다.");
					return result;
				}
				success = svc.adminLevelAdd(dto);
			}
			result.put("success", success);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	// 이미지 삭제 (관리자만 가능)
	@DeleteMapping("/admin/level/delete")
	public Map<String, Object> adminLevelDelete(@RequestParam("lv_idx") int lvIdx, @RequestParam("url") String imageUrl,
			@RequestHeader Map<String, String> header) {

		Map<String, Object> result = new HashMap<>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			// 사용자 보유 여부 체크
			int userCount = svc.adminLevelCnt(lvIdx);
			if (userCount > 0) {
				result.put("success", false);
				result.put("msg", "해당 레벨을 보유한 사용자가 있어 삭제할 수 없습니다.");
				return result;
			}

			// 이미지 삭제 (url이 유효할 때만)
			if (imageUrl.startsWith(urlPrefix)) {
				String relativePath = imageUrl.replace(urlPrefix + "/", "");
				Path filePath = Paths.get(baseUploadPath, relativePath);
				Files.deleteIfExists(filePath);
			}

			// DB 삭제
			boolean deleted = svc.adminLevelDelete(lvIdx);
			if (deleted) {
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "레벨 삭제에 실패했습니다.");
			}

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "서버 오류: " + e.getMessage());
		}

		return result;
	}

	// 관리자 권한 체크
	@GetMapping("/admin/check")
	public Map<String, Object> checkAdminPrivilege(@RequestHeader Map<String, String> header) {
		Map<String, Object> result = new HashMap<>();

		try {
			String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
			if (loginId != null && !loginId.isEmpty()) {
				int userLevel = svc.userLevel(loginId);
				result.put("success", true);
				result.put("isAdmin", userLevel == 7);
			} else {
				result.put("success", false);
				result.put("message", "Invalid token");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Error checking admin privilege");
		}

		return result;
	}

	// ===== 암 종류 관리 =====
	
	// 암 종류 목록 조회
	@GetMapping("/admin/cancer/list")
	public Map<String, Object> getCancerList(@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = null;
		try {
			loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "인증 토큰이 유효하지 않습니다.");
			return result;
		}

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			List<CancerDTO> cancerList = svc.adminCancerList();
			result.put("success", true);
			result.put("cancers", cancerList);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "암 종류 목록을 불러오는데 실패했습니다: " + e.getMessage());
		}
		return result;
	}

	// 암 종류 등록/수정
	@PostMapping("/admin/cancer/save")
	public Map<String, Object> saveCancer(@RequestBody CancerDTO cancerDTO,
			@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			boolean success;
			if (cancerDTO.getCancer_idx() > 0) {
				// 수정
				success = svc.adminCancerUpdate(cancerDTO);
			} else {
				// 등록
				success = svc.adminCancerAdd(cancerDTO);
			}
			result.put("success", success);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	// 암 종류 삭제
	@DeleteMapping("/admin/cancer/delete")
	public Map<String, Object> deleteCancer(@RequestParam("cancer_idx") int cancerIdx,
			@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			// 사용자 보유 여부 체크
			int userCount = svc.adminCancerUserCnt(cancerIdx);
			if (userCount > 0) {
				result.put("success", false);
				result.put("msg", "해당 암 종류를 선택한 사용자가 있어 삭제할 수 없습니다.");
				return result;
			}

			// DB 삭제
			boolean deleted = svc.adminCancerDelete(cancerIdx);
			if (deleted) {
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "암 종류 삭제에 실패했습니다.");
			}

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "서버 오류: " + e.getMessage());
		}

		return result;
	}

	// ===== 병기 관리 =====
	
	// 병기 목록 조회
	@GetMapping("/admin/stage/list")
	public Map<String, Object> getStageList(@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = null;
		try {
			loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "인증 토큰이 유효하지 않습니다.");
			return result;
		}

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			List<StageDTO> stageList = svc.adminStageList();
			result.put("success", true);
			result.put("stages", stageList);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "병기 목록을 불러오는데 실패했습니다: " + e.getMessage());
		}
		return result;
	}

	// 병기 등록/수정
	@PostMapping("/admin/stage/save")
	public Map<String, Object> saveStage(@RequestBody StageDTO stageDTO,
			@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			boolean success;
			if (stageDTO.getStage_idx() > 0) {
				// 수정
				success = svc.adminStageUpdate(stageDTO);
			} else {
				// 등록
				success = svc.adminStageAdd(stageDTO);
			}
			result.put("success", success);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	// 병기 삭제
	@DeleteMapping("/admin/stage/delete")
	public Map<String, Object> deleteStage(@RequestParam("stage_idx") int stageIdx,
			@RequestHeader Map<String, String> header) {
		result = new HashMap<>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");

		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId) != 7) {
			result.put("success", false);
			result.put("msg", "관리자 권한이 필요합니다.");
			return result;
		}

		try {
			// 사용자 보유 여부 체크
			int userCount = svc.adminStageUserCnt(stageIdx);
			if (userCount > 0) {
				result.put("success", false);
				result.put("msg", "해당 병기를 선택한 사용자가 있어 삭제할 수 없습니다.");
				return result;
			}

			// DB 삭제
			boolean deleted = svc.adminStageDelete(stageIdx);
			if (deleted) {
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "병기 삭제에 실패했습니다.");
			}

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "서버 오류: " + e.getMessage());
		}

		return result;
	}
}
