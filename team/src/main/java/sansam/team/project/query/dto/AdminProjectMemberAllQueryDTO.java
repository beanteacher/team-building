package sansam.team.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sansam.team.common.aggregate.YnType;
import sansam.team.common.aggregate.DevelopType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminProjectMemberAllQueryDTO {

    private Long userSeq;
    private Long projectMemberSeq;
    private String userName;
    private YnType projectMemberDelYn;
    private YnType projectMentorYn;
    private DevelopType projectMemberDevelopType;
    private Long projectMemberCommitScore;
}
