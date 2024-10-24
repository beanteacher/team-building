package sansam.team.team.query.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamChatResponse {
    private long teamChatSeq;
    private long teamSeq;
    private String teamChatName;
    private String teamChatComment;
    private long teamMemberSeq;
    private String userNickname;

    private String regDate;
}
