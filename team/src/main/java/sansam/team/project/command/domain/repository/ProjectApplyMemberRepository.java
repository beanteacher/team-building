package sansam.team.project.command.domain.repository;

import sansam.team.project.command.domain.aggregate.entity.ProjectApplyMember;

import java.util.Optional;

public interface ProjectApplyMemberRepository {

    Optional<ProjectApplyMember> findById(Long applyMemberSeq);

    ProjectApplyMember save(ProjectApplyMember projectApplyMember);

    void deleteById(Long projectApplyMemberSeq);


}
