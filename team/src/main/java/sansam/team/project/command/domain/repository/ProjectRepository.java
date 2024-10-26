package sansam.team.project.command.domain.repository;

import sansam.team.project.command.domain.aggregate.entity.Project;

import java.util.Optional;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findById(Long projectSeq);

    void deleteById(Long projectSeq) throws Exception;
}
