package sansam.team.team.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sansam.team.team.query.dto.UserReviewAllQueryDTO;
import sansam.team.team.query.mapper.TeamUserReviewQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamUserReviewQueryService {

    private final TeamUserReviewQueryMapper teamUserReviewQueryMapper;

    /* 회원 평가 전체 조회*/
    public List<UserReviewAllQueryDTO> getUserAllReview(){
        return teamUserReviewQueryMapper.findUserAllReview();
    }

    /* 회원 평가 상세 조회 */
}
