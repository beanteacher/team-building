package sansam.team.team.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.exception.CustomException;
import sansam.team.exception.ErrorCodeType;
import sansam.team.team.command.application.dto.TeamCreateRequest;
import sansam.team.team.command.application.dto.TeamCreateResponseDTO;
import sansam.team.team.command.application.dto.TeamDTO;
import sansam.team.team.command.application.dto.TeamUpdateRequest;
import sansam.team.team.command.domain.aggregate.entity.Team;
import sansam.team.team.command.domain.aggregate.entity.TeamMember;
import sansam.team.team.command.domain.repository.TeamMemberRepository;
import sansam.team.team.command.domain.repository.TeamRepository;
import sansam.team.team.command.domain.repository.TeamScheduleRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<TeamCreateResponseDTO> createTeams(TeamCreateRequest teamCreateRequest) {
        Long projectSeq = teamCreateRequest.getProjectSeq();
        List<TeamCreateResponseDTO> teamCreateResponseList = new ArrayList<>();  // 팀 생성 후 반환할 리스트

        for (TeamDTO teamDTO : teamCreateRequest.getTeams()) {
            // ModelMapper로 TeamDTO를 Team 엔티티로 변환
            Team team = modelMapper.map(teamDTO, Team.class);
            team.ProjectSeq(projectSeq);  // projectSeq는 수동으로 설정
            teamRepository.save(team);

            // userSeq를 기반으로 팀 멤버 추가
            for (Long userSeq : teamDTO.getUserSeqs()) {
                TeamMember teamMember = new TeamMember(userSeq, team.getTeamSeq());
                teamMemberRepository.save(teamMember);
            }

            // 생성된 팀의 teamSeq를 TeamCreateResponseDTO에 담아서 리스트에 추가
            TeamCreateResponseDTO teamCreateResponse = new TeamCreateResponseDTO(team.getTeamSeq(),team.getTeamName());
            teamCreateResponseList.add(teamCreateResponse);
        }

        // 생성된 팀 리스트 반환
        return teamCreateResponseList;
    }


    @Transactional
    public Team updateTeam(Long teamSeq, TeamUpdateRequest request) {
        Team team = teamRepository.findById(teamSeq).orElseThrow();

        team.modifyTeam(request.getTeamName());

        return team;
    }

    @Transactional
    public void deleteTeam(Long teamSeq) {
        teamRepository.deleteById(teamSeq);
    }

    @Transactional
    public Team getTeamById(long teamSeq) throws CustomException {
        return teamRepository.findById(teamSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.TEAM_NOT_FOUND));
    }

}
