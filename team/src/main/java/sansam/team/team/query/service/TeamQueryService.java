package sansam.team.team.query.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sansam.team.security.util.SecurityUtil;
import sansam.team.team.query.dto.*;
import sansam.team.team.query.mapper.TeamQueryMapper;
import sansam.team.user.query.dto.CustomUserDTO;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamQueryService {

    private final TeamQueryMapper teamQueryMapper;

    public List<TeamResponse> selectTeamList(Long projectSeq) {
        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();
        return teamQueryMapper.selectTeamList(new TeamRequest(user.getUserSeq(), user.getUserAuth(), projectSeq));
    }

    public TeamFindByIdResponse selectTeamById(Long teamSeq) {
        return teamQueryMapper.selectTeamById(teamSeq);
    }

    public TeamMemberResponse selectTeamMemberByTeamSeq(Long teamSeq) {
        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();
        return teamQueryMapper.selectTeamMemberByTeamSeq(new TeamMemberRequest(user.getUserSeq(), teamSeq));
    }
}
