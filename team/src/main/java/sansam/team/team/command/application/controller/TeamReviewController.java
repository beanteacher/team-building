package sansam.team.team.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.team.command.application.dto.TeamReviewDTO;
import sansam.team.team.command.application.service.TeamReviewService;
import sansam.team.team.command.domain.aggregate.entity.TeamReview;

@RestController
@RequestMapping("api/vi/team/{teamSeq}/review")
@Tag(name = "3-8. TeamReview API", description = "팀 평가 API")
@RequiredArgsConstructor
public class TeamReviewController {

    private final TeamReviewService teamReviewService;

    @PostMapping
    @Operation(summary = "팀원 평가 추가")
    public ApiResponse<String> createTeamReview(
            @PathVariable Long teamSeq,
            @RequestBody TeamReviewDTO reviewDTO) {
        boolean result = teamReviewService.createTeamReview(reviewDTO);
        return ResponseUtil.successResponse(result ? "팀원 평가 추가 성공" : "팀원 평가 추가 실패").getBody();
    }

    @PutMapping("/{reviewSeq}")
    @Operation(summary = "팀원 평가 수정")
    public ApiResponse<TeamReview> updateTeamReview(
            @PathVariable Long teamSeq,
            @PathVariable long reviewSeq,
            @RequestBody TeamReviewDTO reviewDTO) {
        TeamReview teamReview = teamReviewService.updateTeamReview(reviewSeq, reviewDTO);
        return ResponseUtil.successResponse("팀원 평가 수정 성공", teamReview).getBody();
    }

    @DeleteMapping("/{reviewSeq}")
    @Operation(summary = "팀원 평가 삭제")
    public ApiResponse<String> deleteTeamReview(
            @PathVariable Long teamSeq,
            @PathVariable long reviewSeq) {
        teamReviewService.deleteTeamReview(reviewSeq);
        return ResponseUtil.successResponse("팀원 평가 삭제 성공").getBody();
    }

}

