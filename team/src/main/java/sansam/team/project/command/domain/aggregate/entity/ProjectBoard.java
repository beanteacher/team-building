package sansam.team.project.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import sansam.team.common.aggregate.entity.BaseTimeEntity;
import sansam.team.project.command.application.dto.AdminProjectBoardUpdateDTO;
import sansam.team.project.command.domain.aggregate.BoardStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_project_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProjectBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectBoardSeq;

    private String projectBoardTitle;

    private String projectBoardContent;

    private int projectBoardHeadCount;

    private String projectBoardImgUrl;

    private LocalDateTime projectBoardStartDate;

    private LocalDateTime projectBoardEndDate;

    @Enumerated(EnumType.STRING)
    private BoardStatus projectBoardStatus = BoardStatus.RECRUITMENT;

    private LocalDateTime projectStartDate;

    private LocalDateTime projectEndDate;

    private Long projectBoardAdminSeq;

    private LocalDateTime delDate;

    public void setProjectBoardAdminSeq(Long userSeq) {
        this.projectBoardAdminSeq = userSeq;
    }
}
