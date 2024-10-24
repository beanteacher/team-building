package sansam.team.common.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import sansam.team.common.mongo.MongoDBSequenceCreator;
import sansam.team.common.websocket.dto.TeamChatMemberDTO;
import sansam.team.common.websocket.dto.TeamChatMessageDTO;
import sansam.team.common.websocket.dto.TeamChatMessageType;
import sansam.team.exception.CustomException;
import sansam.team.exception.ErrorCodeType;
import sansam.team.team.command.application.service.TeamChatMessageService;

import java.io.IOException;
import java.util.*;

/*
 * WebSocket Handler 작성
 * 소켓 통신은 서버와 클라이언트가 1:n으로 관계를 맺는다. 따라서 한 서버에 여러 클라이언트 접속 가능
 * 서버에는 여러 클라이언트가 발송한 메세지를 받아 처리해줄 핸들러가 필요
 * TextWebSocketHandler를 상속받아 핸들러 작성
 * 클라이언트로 받은 메세지를 log로 출력하고 클라이언트로 환영 메세지를 보내줌
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final MongoDBSequenceCreator sequenceCreator;
    private final MongoTemplate mongoTemplate;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // chatRoomId: {session1, session2}
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // 페이로드 -> chatMessageDto로 변환
        TeamChatMessageDTO chatDTO = mapper.readValue(payload, TeamChatMessageDTO.class);
        insertTeamChatMessage(chatDTO);

        Long chatRoomId = chatDTO.getTeamChatSeq();  // 팀 채팅 Seq로 채팅방 Id 생성
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }

        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        // message 에 담긴 타입을 확인한다.
        // 이때 message 에서 getType 으로 가져온 내용이
        // ChatDTO 의 열거형인 MessageType 안에 있는 ENTER 과 동일한 값이라면
        if (chatDTO.getMessageType().equals(TeamChatMessageType.ENTER)) {
            // sessions 에 넘어온 session 을 담고,
            chatRoomSession.add(session);
        }
        if (chatRoomSession.size()>=3) {
            removeClosedSession(chatRoomSession);
        }

        sendMessageToChatRoom(chatDTO, chatRoomSession);
    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(TeamChatMessageDTO chatDTO, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatDTO));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void insertTeamChatMessage(TeamChatMessageDTO chatDTO) {
        int count = 0;
        chatDTO.setTeamChatMessageSeq(sequenceCreator.createSeq("teamChatMessageSeq"));

        try {
            if (chatDTO.getMessageType().equals(TeamChatMessageType.ENTER)) {
                Query query = new Query(Criteria.where("teamChatSeq").is(chatDTO.getTeamChatSeq()));
                List<TeamChatMemberDTO> teamChatMemberList = mongoTemplate.find(query, TeamChatMemberDTO.class, "chatMember");
                for(TeamChatMemberDTO teamChatMember : teamChatMemberList) {
                    if(teamChatMember.getTeamMemberSeq() == chatDTO.getTeamMemberSeq()) {
                        count++;
                    }
                }
            }

            if (chatDTO.getMessageType().equals(TeamChatMessageType.TALK) || count == 0) {
                mongoTemplate.insert(chatDTO);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCodeType.MONGO_ERROR);
        }
    }
}