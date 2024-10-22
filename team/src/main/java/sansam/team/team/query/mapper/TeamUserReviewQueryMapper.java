package sansam.team.team.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.team.query.dto.UserReviewAllQueryDTO;

import java.util.List;

@Mapper
public interface TeamUserReviewQueryMapper {


    public List<UserReviewAllQueryDTO> findUserAllReview();
}
