package sansam.team.user.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansam.team.common.response.ApiResponse;
import sansam.team.user.query.dto.UserMemberReviewResponseDTO;
import sansam.team.user.query.dto.UserMentorReviewResponseDTO;
import sansam.team.user.query.service.UserReviewQueryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/{userSeq}/review")
@Tag(name = "1-1. User API", description = "회원 API")
public class UserReviewQueryController {

    private final UserReviewQueryService userReviewQueryService;

    @GetMapping("/teamMember")
    @Operation(summary = "팀원 리뷰 조회", description = "팀원 리뷰 조회 API")
    public ApiResponse<List<UserMemberReviewResponseDTO>> getTeamMemberReviewList(@PathVariable long userSeq) {
        List<UserMemberReviewResponseDTO> memberReviewList = userReviewQueryService.getTeamMemberReviewList(userSeq);
        return ApiResponse.ofSuccess(memberReviewList);
    }

    @GetMapping("/teamMentor")
    @Operation(summary = "멘토 리뷰 조회", description = "멘토 리뷰 조회 API")
    public ApiResponse<List<UserMentorReviewResponseDTO>> getTeamMentorReviewList(@PathVariable long userSeq) {
        List<UserMentorReviewResponseDTO> mentorReviewList = userReviewQueryService.getTeamMentorReviewList(userSeq);
        return ApiResponse.ofSuccess(mentorReviewList);
    }

}
