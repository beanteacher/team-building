package sansam.team.project.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sansam.team.common.aggregate.YnType;
import sansam.team.common.aggregate.entity.BaseTimeEntity;
import sansam.team.project.command.application.dto.AdminProjectMemberUpdateDTO;
import sansam.team.common.aggregate.DevelopType;

@Entity
@Table(name = "tbl_project_member")
@Getter
@NoArgsConstructor
public class ProjectMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectMemberSeq;

    @Enumerated(EnumType.STRING)
    private YnType projectMemberDelYn = YnType.N;

    @Enumerated(EnumType.STRING)
    private YnType projectMentorYn = YnType.N;

    private Long userSeq;

    private Long projectSeq;

    @Enumerated(value = EnumType.STRING)
    private DevelopType projectMemberDevelopType;

    private Long projectMemberCommitScore;

    public ProjectMember(Long userSeq, Long projectSeq, DevelopType projectMemberDevelopType) {
        this.userSeq = userSeq;
        this.projectSeq = projectSeq;
        this.projectMemberDevelopType = projectMemberDevelopType;
    }

    public static ProjectMember createEntity(Long userSeq, Long projectSeq, DevelopType projectMemberDevelopType) {
        return new ProjectMember(userSeq, projectSeq, projectMemberDevelopType);
    }

    public void modifyProjectMember(AdminProjectMemberUpdateDTO updateDTO) {
        if (updateDTO.getProjectMemberDelYn() != null) {
            this.projectMemberDelYn = updateDTO.getProjectMemberDelYn();
        }
        if (updateDTO.getProjectMentorYn() != null) {
            this.projectMentorYn = updateDTO.getProjectMentorYn();
        }
        if(updateDTO.getProjectInterest() != null) {
            this.projectMemberDevelopType = updateDTO.getProjectInterest();
        }
        if(updateDTO.getProjectMemberCommitScore()!= null) {
            this.projectMemberCommitScore = updateDTO.getProjectMemberCommitScore();
        }
    }
}
