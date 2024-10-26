package sansam.team.team.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamReceiveReviewRequest {
    private Long sendTeamMemberSeq;
    private Long receiveTeamMemberSeq;
}
