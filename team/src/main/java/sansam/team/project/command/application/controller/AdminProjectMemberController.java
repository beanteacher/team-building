package sansam.team.project.command.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.project.command.application.dto.AdminProjectMemberUpdateDTO;
import sansam.team.project.command.application.service.AdminProjectMemberService;
import sansam.team.project.command.domain.aggregate.entity.MentorReview;
import sansam.team.project.command.domain.aggregate.entity.ProjectMember;

@RestController
@RequestMapping("/api/v1/admin/project")
@RequiredArgsConstructor
@Tag(name = "2-4. Project Member Admin API", description = "프로젝트 회원 관리자 API")
public class AdminProjectMemberController {

    private final AdminProjectMemberService adminProjectMemberService;

    // 프로젝트 회원 추가 API
    @PostMapping("/{projectSeq}/member")
    @Operation(summary = "프로젝트 회원 추가", description = "프로젝트 회원 추가 API (관리자만 가능)")
    public ApiResponse<?> addProjectMember(
            @PathVariable Long projectSeq,
            @RequestParam Long userSeq) {  // 요청 파라미터로 userSeq 받기

        try {
            // 주어진 userSeq와 프로젝트 정보를 이용해 멤버 추가
            ProjectMember projectMember = adminProjectMemberService.containForProject(projectSeq, userSeq);

            // 성공 응답 반환
            return ResponseUtil.successResponse("Project member created successfully").getBody();
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (예외 발생 시)
            return ResponseUtil.failureResponse(e.getMessage(), "PROJECT_SEQ_NULL").getBody();
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseUtil.exceptionResponse(e, "PROJECT_MEMBER_CREATE_ERROR").getBody();
        }
    }

    // 프로젝트 멤버 수정 엔드포인트
    @PutMapping("/{projectSeq}/member/{projectMemberSeq}")
    @Operation(summary = "프로젝트 회원 상태 변경(탈퇴 유무, 멘토 유무)", description = "프로젝트 수정 API (관리자만 가능)")
    public ApiResponse<?> updateProjectMember(
            @PathVariable Long projectSeq,
            @PathVariable Long projectMemberSeq,
            @RequestBody AdminProjectMemberUpdateDTO updateDTO) {

        try {
            // 프로젝트 회원 상태 변경 요청
            ProjectMember updatedProjectMember = adminProjectMemberService.updateProjectMember(projectMemberSeq, updateDTO);

            // 성공 응답 반환
            return ResponseUtil.successResponse("Project member updated successfully").getBody();
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (예외 발생 시)
            return ResponseUtil.failureResponse(e.getMessage(), "PROJECT_MEMBER_SEQ_NULL").getBody();
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseUtil.exceptionResponse(e, "PROJECT_MEMBER_UPDATE_ERROR").getBody();
        }
    }
}

