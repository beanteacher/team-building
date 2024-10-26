package sansam.team.team.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamUserReviewQueryDTO {

    private Long userReviewSeq;
    private String teamMemberReviewContent;
    private double teamMemberReviewStar;
}
