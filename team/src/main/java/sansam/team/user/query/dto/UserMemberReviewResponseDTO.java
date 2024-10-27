package sansam.team.user.query.dto;

import lombok.Data;

@Data
public class UserMemberReviewResponseDTO {
    private long userReviewSeq;

    private long sendMemberSeq;

    private long receiveMemberSeq;

    private double reviewStar;

    private String reviewContent;

    private String regDate;

    private String modDate;

    private String teamName;

    private String projectTitle;

}
