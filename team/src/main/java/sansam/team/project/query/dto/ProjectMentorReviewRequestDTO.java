package sansam.team.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMentorReviewRequestDTO {
    private Long mentorSeq;        // 멘토 ID
    private Long projectMemberSeq; // 프로젝트 회원 ID

}
