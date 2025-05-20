/*
 * package com.withcare.search.controller;
 * 
 * import java.util.Map;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.CrossOrigin; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestHeader; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.withcare.search.dto.SearchResultDTO; import
 * com.withcare.search.service.SearchService; import com.withcare.util.JwtToken;
 * 
 * @CrossOrigin
 * 
 * @RestController public class SearchController {
 * 
 * @Autowired SearchService svc;
 * 
 * Logger log = LoggerFactory.getLogger(getClass());
 * 
 * // 검색 기능
 * 
 * @RequestMapping("/api/search")
 * 
 * @PostMapping public ResponseEntity<?> search(@RequestBody Map<String, String>
 * param,
 * 
 * @RequestHeader("Authorization") String header) {
 * 
 * String keyword = param.get("keyword"); String token =
 * header.replace("Bearer ", ""); Map<String, Object> tokenData =
 * JwtToken.JwtUtils.readToken(token);
 * 
 * String userId = (String) tokenData.get("id"); if (userId == null ||
 * userId.isEmpty()) { return ResponseEntity.status(401).body("Unauthorized"); }
 * 
 * SearchResultDTO result = svc.searchPosts(userId, keyword); return
 * ResponseEntity.ok(result); }
 * 
 * 
 * 
 * }
 */