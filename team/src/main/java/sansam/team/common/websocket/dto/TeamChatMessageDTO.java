package sansam.team.common.websocket.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class TeamChatMessageDTO {

    private long teamChatMessageSeq;
    private TeamChatMessageType messageType; // 입장 혹은 메시지
    private long teamChatSeq;   // 팀별 채팅방 ID
    private long teamMemberSeq;  // 보낸 사람 팀원 Seq
    private String message;

    private String regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
