package sansam.team.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMentorReviewUserQueryDTO {

    private String projectTitle;
    private String projectMentorName;
    private double mentorReviewStar;
    private String mentorReviewContent;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
