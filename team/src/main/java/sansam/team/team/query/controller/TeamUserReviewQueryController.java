package sansam.team.team.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansam.team.team.query.dto.UserReviewAllQueryDTO;
import sansam.team.team.query.service.TeamUserReviewQueryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team/{teamSeq}/review")
@Tag(name = "3-8. TeamReview API", description = "팀 평가 API")
public class TeamUserReviewQueryController {

    private final TeamUserReviewQueryService teamUserReviewQueryService;

    // 팀의 모든 회원 평가 조회
    @GetMapping()
    @Operation(summary = "팀원 평가 전체 조회")
    public List<UserReviewAllQueryDTO> getAllReviews(@PathVariable("teamSeq") Long teamSeq) {

        return teamUserReviewQueryService.getUserAllReview();
    }

}
