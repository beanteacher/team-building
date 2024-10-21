package sansam.team.team.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.common.aggregate.RoleType;
import sansam.team.common.util.DateTimeUtil;
import sansam.team.exception.CustomException;
import sansam.team.exception.ErrorCodeType;
import sansam.team.security.util.SecurityUtil;
import sansam.team.team.command.application.dto.TeamMemberScheduleDTO;
import sansam.team.team.command.application.dto.TeamScheduleDTO;
import sansam.team.team.command.domain.aggregate.entity.TeamMemberSchedule;
import sansam.team.team.command.domain.aggregate.entity.TeamSchedule;
import sansam.team.team.command.domain.repository.TeamMemberScheduleRepository;
import sansam.team.team.command.domain.repository.TeamScheduleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberScheduleService {

    private final TeamScheduleService teamScheduleService;

    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamMemberScheduleRepository teamMemberScheduleRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public boolean createScheduleByMember(TeamMemberScheduleDTO memberScheduleDTO) {
        boolean result = false;

        try {
            if(isPossibleScheduleByMember(memberScheduleDTO)) {
                teamMemberScheduleRepository.save(modelMapper.map(memberScheduleDTO, TeamMemberSchedule.class));
                result = true;
            }

        } catch (Exception e) {
            if(((CustomException) e).getErrorCode() != null) {
                log.error("createScheduleByMember Error : {}", ((CustomException) e).getErrorCode().getMessage());
                throw new CustomException(((CustomException) e).getErrorCode());
            } else {
                log.error("createScheduleByMember Error : {}", e.getMessage());
                throw new CustomException(ErrorCodeType.COMMON_ERROR);
            }
        }

        return result;
    }

    @Transactional
    public TeamMemberSchedule updateScheduleByMember(long memberScheduleSeq, TeamMemberScheduleDTO memberScheduleDTO) {
        try {
            TeamMemberSchedule teamMemberSchedule = teamMemberScheduleRepository.findById(memberScheduleSeq)
                    .orElseThrow(() -> new CustomException(ErrorCodeType.MEMBER_SCHEDULE_NOT_FOUND));

            if(isPossibleScheduleByMember(memberScheduleDTO)) {
                teamMemberSchedule.updateMemberSchedule(memberScheduleDTO.getMemberScheduleContent(), memberScheduleDTO.getMemberSchedulePercent());
                teamMemberScheduleRepository.save(teamMemberSchedule);
            }

            return teamMemberSchedule;

        } catch (Exception e) {
            if(((CustomException) e).getErrorCode() != null) {
                log.error("updateScheduleByMember Error : {}", ((CustomException) e).getErrorCode().getMessage());
                throw new CustomException(((CustomException) e).getErrorCode());
            } else {
                log.error("updateScheduleByMember Error : {}", e.getMessage());
                throw new CustomException(ErrorCodeType.COMMON_ERROR);
            }
        }
    }

    /* 팀원별 일정 진행상황 입력 조건 체크 */
    public boolean isPossibleScheduleByMember(TeamMemberScheduleDTO memberScheduleDTO) {
        TeamSchedule teamSchedule = teamScheduleRepository.findById(memberScheduleDTO.getTeamScheduleSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.SCHEDULE_NOT_FOUND));

        if(teamScheduleService.isSchedulePeriod(modelMapper.map(teamSchedule, TeamScheduleDTO.class))) {
            if(teamSchedule.getScheduleStartDate() != null && teamSchedule.getScheduleEndDate() != null) {
                if(!DateTimeUtil.isBetweenDateTime(teamSchedule.getScheduleStartDate(), teamSchedule.getScheduleEndDate())) {
                    throw new CustomException(ErrorCodeType.SCHEDULE_PERIOD_ERROR);
                }
            }
        }

        return true;
    }

    @Transactional
    public void deleteScheduleByMember(long memberScheduleSeq) {
        try {
            teamMemberScheduleRepository.deleteById(memberScheduleSeq);
        } catch (Exception e) {
            log.error("deleteScheduleByMember Error : {}", e.getMessage());
            throw new CustomException(ErrorCodeType.COMMON_ERROR);
        }
    }

    @Transactional
    public TeamMemberSchedule feedbackScheduleByMentor(long memberScheduleSeq, TeamMemberScheduleDTO memberScheduleDTO) {
        try {
            TeamMemberSchedule teamMemberSchedule = teamMemberScheduleRepository.findById(memberScheduleSeq)
                    .orElseThrow(() -> new CustomException(ErrorCodeType.MEMBER_SCHEDULE_NOT_FOUND));

            if(isPossibleFeedback(teamMemberSchedule.getTeamScheduleSeq())) {
                teamMemberSchedule.feedbackMemberSchedule(memberScheduleDTO.getMemberScheduleContent(), memberScheduleDTO.getMemberSchedulePercent());
                teamMemberScheduleRepository.save(teamMemberSchedule);
            }

            return teamMemberSchedule;

        } catch (Exception e) {
            if(((CustomException) e).getErrorCode() != null) {
                log.error("feedbackScheduleByMentor Error : {}", ((CustomException) e).getErrorCode().getMessage());
                throw new CustomException(((CustomException) e).getErrorCode());
            } else {
                log.error("feedbackScheduleByMentor Error : {}", e.getMessage());
                throw new CustomException(ErrorCodeType.COMMON_ERROR);
            }
        }
    }

    /* 피드백 가능 조건 체크 */
    public boolean isPossibleFeedback(long teamScheduleSeq) {
        if(!RoleType.MENTOR.equals(SecurityUtil.getAuthenticatedUser().getUserAuth())) {
            throw new CustomException(ErrorCodeType.MENTOR_AUTH_ERROR);
        }

        TeamSchedule teamSchedule = teamScheduleRepository.findById(teamScheduleSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SCHEDULE_NOT_FOUND));

        return teamScheduleService.isSchedulePeriod(modelMapper.map(teamSchedule, TeamScheduleDTO.class));
    }

}
