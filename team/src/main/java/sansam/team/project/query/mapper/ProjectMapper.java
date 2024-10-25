package sansam.team.project.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.project.query.dto.*;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<ProjectAllQueryDTO> findAllProjectForAdmin();

    List<ProjectAllQueryDTO> findAllProjectForUser(Long userSeq);

    AdminProjectQueryDTO findProjectByIdForAdmin(Long projectSeq);

    ProjectQueryDTO findProjectByIdForUser(ProjectQueryResponse projectQueryResponse);

    List<ProjectMemberQueryDTO> findProjectMemberByIdForUser(Long projectSeq);
}
