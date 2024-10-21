package sansam.team.project.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sansam.team.common.aggregate.DevelopType;
import sansam.team.project.command.domain.aggregate.ApplyStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminProjectApplyMemberDTO {

    private Long projectBoardSeq;
    private DevelopType projectMemberDevelopType;
    private ApplyStatus applyStatus;
}
