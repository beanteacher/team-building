package sansam.team.project.command.application.dto;

import lombok.*;
import sansam.team.project.command.domain.aggregate.BoardStatus;
import sansam.team.project.command.domain.aggregate.entity.ProjectBoard;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminProjectBoardUpdateDTO {

    private String projectBoardTitle;
    private String projectBoardContent;
    private int projectBoardHeadCount;
    private String projectBoardImgUrl; // 이미지 URL을 저장하는 필드
    private LocalDateTime projectBoardStartDate;
    private LocalDateTime projectBoardEndDate;
    private BoardStatus boardStatus;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;

    // S3 업로드 후 이미지 URL을 설정하는 메서드
    public void setProjectBoardImgUrlFromS3(String imgUrl) {
        this.projectBoardImgUrl = imgUrl;
    }
}
