package sansam.team.project.command.mapper;

import sansam.team.common.aggregate.DevelopType;
import sansam.team.project.command.domain.aggregate.entity.ProjectMember;

public class ProjectMemberMapper {

    public static ProjectMember toEntity(Long userSeq, Long projectSeq, DevelopType projectMemberDevelopType) {

        return ProjectMember.createEntity(
                userSeq,
                projectSeq,
                projectMemberDevelopType
        );
    }
}
