package sansam.team.team.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sansam.team.team.query.dto.TeamUserReviewAllQueryDTO;
import sansam.team.team.query.dto.TeamUserReviewQueryDTO;

import java.util.List;

@Mapper
public interface TeamUserReviewQueryMapper {


    public List<TeamUserReviewAllQueryDTO> findUserAllReview();

    TeamUserReviewQueryDTO findUserReview(@Param("userReviewSeq") Long userReviewSeq);
}
