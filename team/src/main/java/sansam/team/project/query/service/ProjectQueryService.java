package sansam.team.project.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sansam.team.common.jwt.SecurityUtil;
import sansam.team.project.query.dto.AdminProjectQueryDTO;
import sansam.team.project.query.dto.ProjectAllQueryDTO;
import sansam.team.project.query.dto.ProjectQueryDTO;
import sansam.team.project.query.mapper.ProjectMapper;
import sansam.team.user.command.domain.aggregate.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectQueryService {

    private final ProjectMapper projectMapper;

    /* 프로젝트 전체 조회 (관리자) */
    public List<ProjectAllQueryDTO> getAllProjectsForAdmin() {
        return projectMapper.findAllProjectForAdmin();
    }

    /* 프로젝트 전체 조회 (사용자) */
    public List<ProjectAllQueryDTO> getAllProjectsForUser(Long userSeq){

        User user = SecurityUtil.getAuthenticatedUser();

        if(user.getUserSeq() == null){
            throw new IllegalArgumentException("User Seq is null");
        }

        return projectMapper.findAllProjectForUser(user.getUserSeq());
    }

    /* 프로젝트 상세 조회 (관리자) */
    public AdminProjectQueryDTO getProjectByIdForAdmin(Long projectSeq){
        return projectMapper.findProjectByIdForAdmin(projectSeq);
    }

    /* 프로젝트 상세 조회 (사용자) */
    public ProjectQueryDTO getProjectByIdForUser(Long projectSeq){
        return projectMapper.findProjectByIdForUser(projectSeq);
    }

}
