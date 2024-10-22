package sansam.team.team.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamUserReviewAllQueryDTO {

    private long userReviewSeq;
    private String projectTitle;
    private double reviewStar;
    private LocalDateTime regDate;
}
