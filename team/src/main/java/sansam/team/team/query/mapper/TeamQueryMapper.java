package sansam.team.team.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sansam.team.team.query.dto.*;

import java.util.List;

@Mapper
public interface TeamQueryMapper {
    List<TeamResponse> selectTeamList(TeamRequest teamRequest);

    TeamFindByIdResponse selectTeamById(@Param("teamSeq") Long teamSeq);

    TeamMemberResponse selectTeamMemberByTeamSeq(TeamMemberRequest request);
}
