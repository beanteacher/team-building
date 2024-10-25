package sansam.team.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sansam.team.common.aggregate.DevelopType;
import sansam.team.common.aggregate.YnType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberQueryDTO {

    private Long projectMemberSeq;
    private String userName;
    private YnType projectMemberDelYn;
    private YnType projectMentorYn;
    private YnType userMajorYn;
    private DevelopType projectMemberDevelopType;
    private Long projectMemberCommitScore;
}
