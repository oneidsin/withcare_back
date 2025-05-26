package com.withcare.noti.service;

import com.withcare.noti.dao.NotiDAO;
import com.withcare.noti.dto.NotiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotiService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    NotiDAO dao;

    private static final int LIMIT = 100; // 한 번에 가져올 알림 개수

    private static final long SSE_TIMEOUT = 30 * 60 * 1000L; // SSE 타임아웃 시간은 30분(타임아웃이 발생하면 클라이언트는 자동으로 재연결 시도)

    // 사용자별 SSE 연결을 관리하는 맵
    // key: 사용자 ID, value: 해당 사용자의 SSE 연결 객체
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * SSE 구독 메서드
     * 클라이언트가 실시간 알림을 받기 위해 호출하는 엔드포인트
     *
     * @param user_id 구독을 요청한 사용자의 ID
     * @return SseEmitter SSE 연결 객체
     */

    // SeeEmitter : 스프링에서 SSE 구현을 위한 클래스
    // 서버 -> 클라이언트로 데이터를 실시간으로 푸시하는데 사용
    public SseEmitter subscribe(String user_id) {
        // 이미 해당 사용자의 연결이 있다면 제거
        // (중복 연결 방지 및 리소스 정리)
        if (emitters.containsKey(user_id)) {
            emitters.get(user_id).complete();
            emitters.remove(user_id);
        }

        // 새로운 SSE 연결 객체 생성
        // 타임아웃 시간을 30분으로 설정
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        // 타임아웃 발생 시 처리
        emitter.onTimeout(() -> {
            log.info("SSE 타임아웃 - user: {}", user_id);
            emitters.remove(user_id);
        });

        // 연결 완료 시 처리
        emitter.onCompletion(() -> {
            log.info("SSE 연결 완료 - user: {}", user_id);
            emitters.remove(user_id);
        });

        // 에러 발생 시 처리
        emitter.onError(ex -> {
            log.error("SSE 에러 - user: {}, error: {}", user_id, ex.getMessage());
            emitters.remove(user_id);
        });

        // 새로 생성한 연결을 맵에 저장
        emitters.put(user_id, emitter);

        // 연결 직후 초기 이벤트 전송
        // 클라이언트가 연결이 성공했는지 확인할 수 있음
        try {
            emitter.send(SseEmitter.event()
                    .name("connect") // 이벤트 이름
                    .data("connected!") // 전송할 데이터
                    .reconnectTime(1000L)); // 연결이 끊어졌을 때 1초 후 재연결 시도
        } catch (IOException e) {
            log.error("SSE 연결 실패 - user: {}", user_id, e);
            emitters.remove(user_id);
        }

        return emitter;
    }

    /**
     * 알림 전송 메서드
     * 특정 사용자에게 실시간 알림을 전송
     *
     * @param user_id 알림을 받을 사용자 ID
     * @param noti    전송할 알림 데이터
     */
    private void sendNotification(String user_id, NotiDTO noti) {
        // 해당 사용자의 SSE 연결 객체 조회
        SseEmitter emitter = emitters.get(user_id);
        if (emitter != null) {
            try {
                // SSE 이벤트 전송
                emitter.send(SseEmitter.event()
                        .name("notification") // 이벤트 이름
                        .data(noti) // 알림 데이터
                        .reconnectTime(1000L)); // 연결이 끊어졌을 때 1초 후 재연결 시도
            } catch (IOException e) {
                // IO 예외 발생 시 (연결이 끊어진 경우)
                log.error("알림 전송 실패 - user: {}, noti: {}", user_id, noti.getNoti_type(), e);
                emitters.remove(user_id);
            } catch (Exception e) {
                // 기타 예외 발생 시
                log.error("알림 전송 중 예외 발생 - user: {}, noti: {}", user_id, noti.getNoti_type(), e);
                emitters.remove(user_id);
            }
        } else {
            // 해당 사용자의 연결이 없는 경우
            log.debug("알림 수신자 연결 없음 - user: {}, noti: {}", user_id, noti.getNoti_type());
        }
    }

    // 사용자의 알림 목록 가져오기(100개 제한)
    public List<Map<String, Object>> getNoti(String id, int offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("limit", LIMIT);
        params.put("offset", offset);
        return dao.getNoti(params);
    }

    // 알림이 100개를 초과하면 오래된 알림부터 삭제
    private void maintainNotificationLimit(String user_id) {

        // 현재 알림 개수 확인
        int count = dao.getNotiCount(user_id);

        // 알림이 100개를 초과하면 오래된 알림 삭제
        if (count > LIMIT) {
            int deleteCount = count - LIMIT;
            log.info("알림 개수 초과 - user: {}, 삭제할 알림 수: {}", user_id, deleteCount);
            dao.deleteOldNoti(user_id, deleteCount);
        }
    }

    // 댓글 알림 저장
    public void sendCommentNoti(int com_idx, int post_idx, String sender_id, String receiver_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setRelate_item_id(com_idx);
        notiDTO.setPost_idx(post_idx);
        notiDTO.setNoti_sender_id(sender_id);
        notiDTO.setRelate_user_id(receiver_id);
        notiDTO.setNoti_type("comment");
        notiDTO.setContent_pre(sender_id + " 님이 회원님의 게시글에 댓글을 남겼습니다.");
        dao.insertNoti(notiDTO);
        maintainNotificationLimit(receiver_id);
        sendNotification(receiver_id, notiDTO);
    }

    // 멘션 알림 저장
    public void sendMentionNoti(int com_idx, int post_idx, String sender_id, String receiver_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setRelate_item_id(com_idx);
        notiDTO.setPost_idx(post_idx);
        notiDTO.setNoti_sender_id(sender_id);
        notiDTO.setRelate_user_id(receiver_id);
        notiDTO.setNoti_type("mention");
        notiDTO.setContent_pre(sender_id + " 님이 회원님을 멘션했습니다.");
        dao.insertNoti(notiDTO);
        maintainNotificationLimit(receiver_id);
        sendNotification(receiver_id, notiDTO);
    }

    // 쪽지 알림 저장
    public void sendMessageNoti(int msg_idx, String sender_id, String receiver_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setRelate_item_id(msg_idx);
        notiDTO.setNoti_sender_id(sender_id);
        notiDTO.setRelate_user_id(receiver_id);
        notiDTO.setNoti_type("message");
        notiDTO.setContent_pre(sender_id + " 님이 회원님에게 쪽지를 보냈습니다.");
        dao.insertNoti(notiDTO);
        maintainNotificationLimit(receiver_id);
        sendNotification(receiver_id, notiDTO);
    }

    // 알림 삭제(1개)
    public boolean deleteNoti(String id, int noti_idx) {
        int row = dao.deleteNoti(id, noti_idx);
        return row > 0;
    }

    // 알림 전체 삭제
    public boolean deleteAllNoti(String id) {
        int row = dao.deleteAllNoti(id);
        return row > 0;
    }

    // 알림 읽음 확인
    public boolean readNoti(String id, int noti_idx) {
        int row = dao.readNoti(id, noti_idx);
        return row > 0;
    }

    // 신고 알림 저장 및 전송
    public void sendReportNoti(int rep_idx, String rep_item_type, String reporter_id, String admin_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setRelate_item_id(rep_idx);
        notiDTO.setNoti_sender_id(reporter_id);
        notiDTO.setRelate_user_id(admin_id);
        notiDTO.setNoti_type("report");
        notiDTO.setContent_pre(reporter_id + " 님이 " + rep_item_type + "에 대한 신고를 접수했습니다.");
        dao.insertNoti(notiDTO);
        maintainNotificationLimit(admin_id);
        sendNotification(admin_id, notiDTO);
    }
}
