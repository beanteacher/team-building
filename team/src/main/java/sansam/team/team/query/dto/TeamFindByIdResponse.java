package sansam.team.team.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TeamFindByIdResponse {
    private Long teamSeq;
    private String teamName;
    private TeamChatResponse teamChatResponse;
    private List<TeamMemberResponse> teamMemberList;
    private List<TeamScheduleResponse> teamScheduleList;
}
