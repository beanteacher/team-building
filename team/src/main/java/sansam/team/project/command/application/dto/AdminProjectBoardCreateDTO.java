package sansam.team.project.command.application.dto;

import lombok.*;
import sansam.team.project.command.domain.aggregate.BoardStatus;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminProjectBoardCreateDTO {

    private String projectBoardTitle;
    private String projectBoardContent;
    private int projectBoardHeadCount;
    private String projectBoardImgUrl;
    private LocalDateTime projectBoardStartDate;
    private LocalDateTime projectBoardEndDate;
    private BoardStatus projectBoardStatus;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
    private Long projectBoardAdminSeq;

}
