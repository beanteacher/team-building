package sansam.team.team.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.exception.CustomException;
import sansam.team.exception.ErrorCodeType;
import sansam.team.team.command.application.dto.TeamScheduleDTO;
import sansam.team.team.command.domain.aggregate.TeamStatusType;
import sansam.team.team.command.domain.aggregate.entity.Team;
import sansam.team.team.command.domain.aggregate.entity.TeamSchedule;
import sansam.team.team.command.domain.repository.TeamScheduleRepository;
import sansam.team.team.command.mapper.TeamScheduleMapper;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamScheduleService {

    private final TeamService teamService;

    private final TeamScheduleRepository teamScheduleRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public boolean createTeamSchedule(long teamSeq, TeamScheduleDTO scheduleDTO) {
        try {
            if(isSchedulePeriod(teamSeq)) {
                TeamSchedule teamSchedule = TeamScheduleMapper.toEntity(teamSeq, scheduleDTO);
                teamScheduleRepository.save(teamSchedule);
                return true;
            }

        } catch (Exception e) {
            if(((CustomException) e).getErrorCode() != null) {
                log.error("createTeamSchedule Error : {}", ((CustomException) e).getErrorCode().getMessage());
                throw new CustomException(((CustomException) e).getErrorCode());
            } else {
                log.error("createTeamSchedule Error : {}", e.getMessage());
                throw new CustomException(ErrorCodeType.COMMON_ERROR);
            }
        }

        return false;
    }

    @Transactional
    public TeamSchedule updateTeamSchedule(long teamSeq, long scheduleSeq, TeamScheduleDTO scheduleDTO) {
        try {
            TeamSchedule teamSchedule = teamScheduleRepository.findById(scheduleSeq)
                    .orElseThrow(() -> new CustomException(ErrorCodeType.SCHEDULE_NOT_FOUND));

            if(isSchedulePeriod(teamSeq)) {
                teamSchedule.updateSchedule(scheduleDTO.getScheduleContent(), scheduleDTO.getScheduleStartDate(), scheduleDTO.getScheduleEndDate());
                teamScheduleRepository.save(teamSchedule);
            }

            return teamSchedule;

        } catch (Exception e) {
            if(((CustomException) e).getErrorCode() != null) {
                log.error("updateTeamSchedule Error : {}", ((CustomException) e).getErrorCode().getMessage());
                throw new CustomException(((CustomException) e).getErrorCode());
            } else {
                log.error("updateTeamSchedule Error : {}", e.getMessage());
                throw new CustomException(ErrorCodeType.COMMON_ERROR);
            }
        }
    }

    /* 팀 일정 입력 가능 조건 체크 */
    public boolean isSchedulePeriod(long teamSeq) {
        Team team = teamService.getTeamById(teamSeq);

        if (TeamStatusType.CLOSE.equals(team.getTeamStatus())) {
            throw new CustomException(ErrorCodeType.TEAM_STATUS_ERROR);
        }

        if(team.getEndDate()!= null && team.getEndDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCodeType.TEAM_END_ERROR);
        }

        return true;
    }

    @Transactional
    public void deleteTeamSchedule(long scheduleSeq) {
        try {
            teamScheduleRepository.deleteById(scheduleSeq);

        } catch (Exception e) {
            log.error("deleteScheduleByMember Error : {}", e.getMessage());
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }
    }

}
