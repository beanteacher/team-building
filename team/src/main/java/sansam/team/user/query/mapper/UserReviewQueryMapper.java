package sansam.team.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.user.query.dto.UserMemberReviewResponseDTO;
import sansam.team.user.query.dto.UserMentorReviewResponseDTO;

import java.util.List;

@Mapper
public interface UserReviewQueryMapper {

    List<UserMemberReviewResponseDTO> getTeamMemberReviewList(long userSeq);

    List<UserMentorReviewResponseDTO> getTeamMentorReviewList(long userSeq);

}
