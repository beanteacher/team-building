package sansam.team.project.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.project.query.dto.project.ProjectAdminQueryDTO;
import sansam.team.project.query.dto.project.ProjectAllQueryDTO;
import sansam.team.project.query.dto.project.ProjectUserQueryDTO;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<ProjectAllQueryDTO> findAllProjectForAdmin();

    List<ProjectAllQueryDTO> findAllProjectForUser(Long userSeq);

    ProjectAdminQueryDTO findProjectByIdForAdmin(Long projectSeq);

    ProjectUserQueryDTO findProjectByIdForUser(Long projectSeq);


}