package sansam.team.project.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.project.query.dto.*;
import sansam.team.project.query.service.ProjectMentorReviewQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "2-6. Project Mentor Review API", description = "프로젝트 강사 평가 API")
public class ProjectMentorReviewQueryController {

    private final ProjectMentorReviewQueryService projectMentorReviewQueryService;

    /* 강사 - 프로젝트 내 회원 평가한 내용 전체 조회 */
    @GetMapping("/project/{projectSeq}/mentor/review")
    @Operation(summary = "프로젝트 별 강사 평가 전체 조회 API", description = "강사가 프로젝트 내 회원 평가한 모든 내용을 조회합니다.")
    public ApiResponse<List<ProjectMentorReviewAllQueryDTO>> getAllProjectMentorReviews(@PathVariable Long projectSeq) {
        List<ProjectMentorReviewAllQueryDTO> reviews = projectMentorReviewQueryService.getAllProjectMentorReview();
        return ApiResponse.ofSuccess(reviews);
    }

    /* 강사 - 프로젝트 내 특정 회원에 대한 평가 조회 */
    @PostMapping("/mentor/review/member")
    @Operation(summary = "특정 프로젝트 내 특정 회원에 대한 멘토의 평가 조회 API", description = "멘토가 프로젝트 내 특정 회원에 대해 작성한 평가를 조회합니다.")
    public ApiResponse<ProjectMentorReviewQueryDTO> getProjectMentorReview(
            @RequestBody ProjectMentorReviewRequestDTO requestDTO) {
        ProjectMentorReviewQueryDTO review = projectMentorReviewQueryService.getProjectMentorReview(
                requestDTO.getMentorSeq(),
                requestDTO.getProjectMemberSeq()
        );
        return ApiResponse.ofSuccess(review);
    }



    /* 회원 - 프로젝트 내 강사 평가 전체 조회 */
    @GetMapping("/user/{userSeq}/mentor/review")
    @Operation(summary = "프로젝트 별 회원의 강사 평가 전체 조회 API", description = "회원이 프로젝트 내 자신이 받은 모든 강사의 평가를 전체 조회합니다.")
    public ApiResponse<List<ProjectMentorReviewAllUserQueryDTO>> getProjectMentorReviewAllByIdForUser(@PathVariable Long userSeq) {
        List<ProjectMentorReviewAllUserQueryDTO> reviews = projectMentorReviewQueryService.getProjectMentorReviewAllByIdForUser();
        return ApiResponse.ofSuccess(reviews);
    }

    /* 회원 - 프로젝트 내 강사 평가 상세 조회 */
    @GetMapping("/user/{userSeq}/mentor/review/{mentorReviewSeq}")
    @Operation(summary = "프로젝트 별 회원의 강사 평가 조회 API", description = "회원이 프로젝트 내 자신이 받은 강사의 평가를 상세 조회합니다.")
    public ApiResponse<ProjectMentorReviewUserQueryDTO> getProjectMentorReviewByIdForUser(
            @PathVariable Long userSeq,
            @PathVariable Long mentorReviewSeq) {
        ProjectMentorReviewUserQueryDTO review = projectMentorReviewQueryService.getProjectMentorReviewByIdForUser(mentorReviewSeq);
        return ApiResponse.ofSuccess(review);
    }
}
