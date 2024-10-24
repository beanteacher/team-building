package sansam.team.common.websocket;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import sansam.team.common.websocket.dto.TeamChatMemberDTO;
import sansam.team.common.websocket.dto.TeamChatMessageDTO;
import sansam.team.common.websocket.dto.TeamChatMessageType;
import sansam.team.team.query.dto.TeamChatRoomResponse;

@Component
@RequiredArgsConstructor
public class WebSocketClient extends WebSocketListener {
    private final OkHttpClient client = new OkHttpClient();
    private final MongoTemplate mongoTemplate;

    private WebSocket webSocket;
    private String userNickname;
    private Long teamChatSeq;
    private Long teamMemberSeq;
    private TeamChatMemberDTO teamChatMember;

    public void start(TeamChatRoomResponse teamChatRoom) {
        Request request = new Request.Builder()
                .url("ws://localhost:8086/ws/chat/" + teamChatRoom.getTeamChatSeq())
                .build();

        this.teamChatSeq = teamChatRoom.getTeamChatSeq();
        this.userNickname = teamChatRoom.getUserNickname();
        this.teamMemberSeq = teamChatRoom.getTeamMemberSeq();

        webSocket = client.newWebSocket(request, this); // this = WebSocketListner
    }

    public void sendMessage(TeamChatMessageDTO teamChatMessage) {
        webSocket.send(makeMessage(teamChatMessage));
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Connected to server");
        if(teamChatMember == null) {
            TeamChatMessageDTO teamChatMessage = new TeamChatMessageDTO();
            teamChatMessage.setMessageType(TeamChatMessageType.ENTER);
            teamChatMessage.setTeamChatSeq(teamChatSeq);
            teamChatMessage.setTeamMemberSeq(teamMemberSeq);
            teamChatMessage.setMessage(userNickname + "님이 입장하였습니다.");

            mongoTemplate.insert(teamChatMessage);

            webSocket.send(makeMessage(teamChatMessage));
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("Disconnected from server: " + reason);
        if(teamChatMember == null) {
            TeamChatMessageDTO teamChatMessage = new TeamChatMessageDTO();
            teamChatMessage.setMessageType(TeamChatMessageType.LEAVE);
            teamChatMessage.setTeamChatSeq(teamChatSeq);
            teamChatMessage.setTeamMemberSeq(teamMemberSeq);
            teamChatMessage.setMessage(userNickname + "님이 채팅방을 나갔습니다.");

            mongoTemplate.insert(teamChatMessage);

            webSocket.send(makeMessage(teamChatMessage));
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

    public String makeMessage(TeamChatMessageDTO teamChatMessageDTO) {
        String messageJson = "{"
                + "\"messageType\" : \""+ teamChatMessageDTO.getMessageType() +"\","
                + "\"teamChatSeq\" : \"" + teamChatMessageDTO.getTeamChatSeq() + "\","
                + "\"teamMemberSeq\" : \"" + teamChatMessageDTO.getTeamMemberSeq() + "\","
                + "\"message\" : \"" + teamChatMessageDTO.getMessage() + "\""
                + "}";

        return messageJson;
    }
}
