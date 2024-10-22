package sansam.team.team.command.application.dto;

import lombok.*;
import sansam.team.common.aggregate.DevelopType;

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
    private DevelopType developType;
}
