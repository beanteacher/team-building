package sansam.team.project.query.dto;

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
public class ProjectApplyMemberQueryDTO {

    private Long userSeq;          // 사용자 ID
    private Long projectApplyMemberSeq;
    private String userName;       // 사용자 이름
    private String userNickname;   // 사용자 닉네임
    private ApplyStatus applyStatus; // 신청 상태
    private DevelopType projectMemberDevelopType; // 신청 회원 관심분야
}
