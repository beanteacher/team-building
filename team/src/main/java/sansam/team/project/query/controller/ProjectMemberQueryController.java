package sansam.team.project.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansam.team.common.response.ApiResponse;
import sansam.team.project.query.dto.ProjectAllQueryDTO;
import sansam.team.project.query.dto.ProjectMemberQueryDTO;
import sansam.team.project.query.dto.ProjectQueryDTO;
import sansam.team.project.query.service.ProjectQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@Tag(name = "2-5. Project Member API", description = "프로젝트 회원 API")
public class ProjectMemberQueryController {

    private final ProjectQueryService projectQueryService;

    /**
     * 프로젝트 전체 조회 (사용자용)
     */
    @GetMapping
    @Operation(summary = "회원 별 프로젝트 전체 조회", description = "해당 사용자가 들어간 프로젝트 전체 조회")
    public ApiResponse<List<ProjectAllQueryDTO>> getAllProjectsForUser() {
        List<ProjectAllQueryDTO> projects = projectQueryService.getAllProjectsForUser(null);
        return ApiResponse.ofSuccess(projects);
    }

    /**
     * 프로젝트 상세 조회 (사용자용)
     */
    @GetMapping("/{projectSeq}")
    @Operation(summary = "회원 별 프로젝트 상세 조회", description = "해당 사용자가 들어간 프로젝트 상세 조회")
    public ApiResponse<ProjectQueryDTO> getProjectByIdForUser(@PathVariable Long projectSeq) {
        ProjectQueryDTO project = projectQueryService.getProjectByIdForUser(projectSeq);
        return ApiResponse.ofSuccess(project);
    }

    /*
    * 프로젝트 참여 멤버 조회 (강사용)
    * */
    @GetMapping("/member/{projectSeq}")
    @Operation(summary = "강사 프로젝트 멤버 리스트 조회", description = "해당 사용자가 들어간 프로젝트 상세 조회")
    public ApiResponse<List<ProjectMemberQueryDTO>> getProjectMemberByIdForUser(@PathVariable Long projectSeq) {
        List<ProjectMemberQueryDTO> projectList = projectQueryService.getProjectMemberByIdForUser(projectSeq);
        return ApiResponse.ofSuccess(projectList);
    }
}
