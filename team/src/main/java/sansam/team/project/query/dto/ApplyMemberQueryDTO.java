package sansam.team.project.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sansam.team.project.command.domain.aggregate.ApplyStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyMemberQueryDTO {

    private Long projectApplyMemberSeq;
    private Long projectBoardSeq;
    private ApplyStatus applyStatus;        // 신청 상태
    private String projectBoardTitle;       // 프로젝트 제목
    private String projectBoardContent;     // 프로젝트 내용
    private int projectBoardHeadCount;      // 프로젝트 참여 인원 수
    private String projectBoardImgUrl;      // 프로젝트 이미지 URL
    private LocalDateTime projectBoardStartDate; // 프로젝트 시작일
    private LocalDateTime projectBoardEndDate;   // 프로젝트 종료일
    private LocalDateTime projectStartDate; // 프로젝트 시작 날짜
    private LocalDateTime projectEndDate;   // 프로젝트 종료 날짜
}
