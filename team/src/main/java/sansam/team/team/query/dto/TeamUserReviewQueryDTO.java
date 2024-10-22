package sansam.team.team.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamUserReviewQueryDTO {

    private String projectTitle;
    private double reviewStar;
    private String reviewContent;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
