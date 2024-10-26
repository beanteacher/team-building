package sansam.team.project.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sansam.team.project.query.dto.*;
import sansam.team.security.util.SecurityUtil;
import sansam.team.project.query.mapper.ProjectMapper;
import sansam.team.team.query.dto.TeamResponse;
import sansam.team.user.query.dto.CustomUserDTO;

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

        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();

        if(ObjectUtils.isEmpty(user.getUserSeq())){
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
        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();

        if(ObjectUtils.isEmpty(user.getUserSeq())){
            throw new IllegalArgumentException("User Seq is null");
        }

        return projectMapper.findProjectByIdForUser(new ProjectQueryResponse(projectSeq, user.getUserSeq()));
    }

    public List<ProjectMemberQueryDTO> getProjectMemberByIdForUser(Long projectSeq) {
        return projectMapper.findProjectMemberByIdForUser(projectSeq);
    }

    public List<TeamResponse> selectTeamListForMentor(Long projectSeq) {
        return projectMapper.selectTeamListForMentor(projectSeq);
    }
}
