package sansam.team.team.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.common.util.DateTimeUtil;
import sansam.team.exception.CustomNotFoundException;
import sansam.team.exception.ErrorCodeType;
import sansam.team.team.command.application.dto.TeamReviewDTO;
import sansam.team.team.command.domain.aggregate.TeamStatusType;
import sansam.team.team.command.domain.aggregate.entity.Team;
import sansam.team.team.command.domain.aggregate.entity.TeamMember;
import sansam.team.team.command.domain.aggregate.entity.TeamReview;
import sansam.team.team.command.domain.repository.TeamReviewRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamReviewService {
    private final TeamReviewRepository teamReviewRepository;
    private final TeamMemberService teamMemberService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @Transactional
    public boolean createTeamReview(TeamReviewDTO teamReviewDTO) {
        boolean result = false;

        try {
            if(isReviewPeriod(teamReviewDTO)) {
                teamReviewRepository.save(modelMapper.map(teamReviewDTO, TeamReview.class));
                result = true;
            }

        } catch (Exception e) {
            if(((CustomNotFoundException) e).getErrorCode() != null) {
                log.error("teamReview create Error : {}", ((CustomNotFoundException) e).getErrorCode().getMessage());
                throw new CustomNotFoundException(((CustomNotFoundException) e).getErrorCode());
            } else {
                throw new CustomNotFoundException(ErrorCodeType.REVIEW_CREATE_ERROR);
            }
        }

        return result;
    }

    public boolean isReviewPeriod(TeamReviewDTO teamReviewDTO) {
        TeamMember teamMember = teamMemberService.getTeamMemberByUserId(teamReviewDTO.getUserReviewSeq());
        Team team = teamService.getTeamById(teamMember.getTeamSeq());

        if(!(DateTimeUtil.isBeforeWeek(team.getEndDate(), 2) && team.getTeamStatus().equals(TeamStatusType.CLOSE))) {
            throw new CustomNotFoundException(ErrorCodeType.REVIEW_CREATE_TIME_ERROR);
        }

        return true;
    }

    @Transactional
    public TeamReview updateTeamReview(long reviewSeq, TeamReviewDTO reviewDTO) {
        try {
            TeamReview teamReview = teamReviewRepository.findById(reviewSeq)
                    .orElseThrow(() -> new CustomNotFoundException(ErrorCodeType.REVIEW_NOT_FOUND));

            if(isReviewPeriod(modelMapper.map(teamReview, TeamReviewDTO.class))) {
                teamReview.updateReview(reviewDTO.getReceiveMemberSeq(), reviewDTO.getReviewStar(), reviewDTO.getReviewContent());
                teamReviewRepository.save(teamReview);
            }

            return teamReview;

        } catch (Exception e) {
            if(((CustomNotFoundException) e).getErrorCode() != null) {
                log.error("teamReview create Error : {}", ((CustomNotFoundException) e).getErrorCode().getMessage());
                throw new CustomNotFoundException(((CustomNotFoundException) e).getErrorCode());
            } else {
                throw new CustomNotFoundException(ErrorCodeType.REVIEW_CREATE_ERROR);
            }
        }
    }

    @Transactional
    public void deleteTeamReview(long reviewSeq) {
        try {
            teamReviewRepository.deleteById(reviewSeq);

        } catch (Exception e) {
            throw new CustomNotFoundException(ErrorCodeType.REVIEW_DELETE_ERROR);
        }
    }
}