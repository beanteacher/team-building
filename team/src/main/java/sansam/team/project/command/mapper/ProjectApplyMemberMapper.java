package sansam.team.project.command.mapper;

import sansam.team.project.command.application.dto.ProjectApplyMemberRequestDTO;
import sansam.team.project.command.domain.aggregate.entity.ProjectApplyMember;

public class ProjectApplyMemberMapper {
    public static ProjectApplyMember toEntity(Long userSeq, ProjectApplyMemberRequestDTO applyMemberDTO) {

        return ProjectApplyMember.createEntity(
                applyMemberDTO.getApplyStatus(),
                applyMemberDTO.getProjectBoardSeq(),
                applyMemberDTO.getProjectMemberDevelopType(),
                userSeq
        );

    }
}
