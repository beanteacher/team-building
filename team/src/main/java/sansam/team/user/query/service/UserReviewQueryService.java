package sansam.team.user.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sansam.team.user.query.dto.UserMemberReviewResponseDTO;
import sansam.team.user.query.dto.UserMentorReviewResponseDTO;
import sansam.team.user.query.mapper.UserReviewQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReviewQueryService {

    private final UserReviewQueryMapper userReviewQueryMapper;

    public List<UserMemberReviewResponseDTO> getTeamMemberReviewList(long userSeq) {
        return userReviewQueryMapper.getTeamMemberReviewList(userSeq);
    }


    public List<UserMentorReviewResponseDTO> getTeamMentorReviewList(long userSeq) {
        return userReviewQueryMapper.getTeamMentorReviewList(userSeq);
    }

}
