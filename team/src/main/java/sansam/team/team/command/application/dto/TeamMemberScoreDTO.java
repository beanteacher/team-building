package sansam.team.team.command.application.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberScoreDTO {
    private Long userSeq;
    private String userName;
    private int githubScore;
    private int careerScore;
    private int majorScore;
    private double teamEvaluationScore;
    private double mentorEvaluationScore;
    private double totalEvaluationScore;
}
