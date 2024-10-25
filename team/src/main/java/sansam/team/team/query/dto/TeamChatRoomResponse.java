package sansam.team.team.query.dto;

import lombok.*;
import sansam.team.common.websocket.dto.TeamChatMemberDTO;
import sansam.team.common.websocket.dto.TeamChatMessageDTO;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamChatRoomResponse {
    private long teamChatSeq;
    private long teamSeq;
    private String teamChatName;
    private String teamChatComment;
    private long teamMemberSeq;
    private String userNickname;

    private List<TeamChatMessageDTO> teamChatMessageList;
    private List<TeamChatMemberDTO> teamChatMemberList;

    private TeamChatMemberDTO teamChatMember;
}
