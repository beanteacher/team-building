package sansam.team.team.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamMemberRequest {
    private Long userSeq;
    private Long teamSeq;
}
