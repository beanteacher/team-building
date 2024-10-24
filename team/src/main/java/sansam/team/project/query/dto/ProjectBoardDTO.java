package sansam.team.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBoardDTO {

    /* 사용자 전용 프로젝트 게시물 상세 조회 DTO */
    private Long projectBoardSeq;
    private String projectBoardTitle;
    private String projectBoardContent;
    private int projectBoardHeadCount;
    private String projectBoardImgUrl;
    private String projectBoardStatus;
    private LocalDateTime projectBoardStartDate;
    private LocalDateTime projectBoardEndDate;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;

}
