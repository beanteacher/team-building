package sansam.team.team.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.project.command.domain.aggregate.entity.MentorReview;
import sansam.team.project.command.domain.repository.ProjectMentorReviewRepository;
import sansam.team.team.command.application.dto.TeamBuildingResultDTO;
import sansam.team.team.command.application.dto.TeamMemberScoreDTO;
import sansam.team.team.command.application.dto.TeamBuildingRuleDTO;
import sansam.team.team.command.domain.aggregate.entity.TeamBuildingRule;
import sansam.team.team.command.domain.repository.TeamBuildingRuleRepository;
import sansam.team.common.aggregate.YnType;
import sansam.team.common.github.GithubUtil;
import sansam.team.project.command.application.dto.AdminProjectMemberUpdateDTO;
import sansam.team.common.aggregate.DevelopType;
import sansam.team.project.command.domain.aggregate.entity.ProjectMember;
import sansam.team.project.command.domain.repository.ProjectMemberRepository;
import sansam.team.team.command.application.dto.TeamBuildingDTO;
import sansam.team.team.command.domain.aggregate.entity.Team;
import sansam.team.team.command.domain.aggregate.entity.TeamReview;
import sansam.team.team.command.domain.repository.TeamMemberRepository;
import sansam.team.team.command.domain.repository.TeamRepository;
import sansam.team.team.command.domain.repository.TeamReviewRepository;
import sansam.team.user.command.domain.aggregate.entity.User;
import sansam.team.user.command.domain.aggregate.entity.UserGithubRepository;
import sansam.team.user.command.domain.repository.UserGithubRepositoryRepository;
import sansam.team.user.command.domain.repository.UserRepository;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamBuildingService {

    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TeamRepository teamRepository;
    private final GithubUtil githubUtil;
    private final TeamReviewRepository teamReviewRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamBuildingRuleRepository buildingRuleRepository;
    private final ProjectMentorReviewRepository projectMentorReviewRepository;
    private final UserGithubRepositoryRepository userGithubRepositoryRepository;

    // 1. 깃허브 커밋 점수 계산 로직
    public int calculateCommitScore(TeamBuildingDTO teamBuildingDTO) throws IOException {
        User user = userRepository.findById(teamBuildingDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));


        ProjectMember pjMember = projectMemberRepository.findById(teamBuildingDTO.getProjectMemberSeq())
                .orElseThrow(() -> new RuntimeException("프로젝트 정보가 존재하지 않습니다."));

        List<UserGithubRepository> userGithubRepositories = userGithubRepositoryRepository.findAllByUserSeq(user.getUserSeq());
        Map<DevelopType,Integer> CommitCntByDevelopType = githubUtil.analyzeCommitCountByDevelopType(userGithubRepositories,user.getUserGithubId());

        int commitCnt = CommitCntByDevelopType.get(pjMember.getProjectMemberDevelopType());

        // GitHub 커밋 점수 계산 로직
        long commitScore;
        if(commitCnt < 50){
            commitScore = 0L;
        }
        else if(commitCnt < 100){
            commitScore = 1L;
        }
        else if(commitCnt < 200){
            commitScore = 2L;
        }
        else if(commitCnt < 500){
            commitScore = 3L;
        }
        else if(commitCnt < 1000){
            commitScore = 4L;
        }
        else{
            commitScore = 5L;
        }

        //pjMember commitScore 업데이트
        AdminProjectMemberUpdateDTO pjMemberUpdateDTO = new AdminProjectMemberUpdateDTO();
        pjMemberUpdateDTO.setProjectMemberCommitScore(commitScore);
        pjMember.modifyProjectMember(pjMemberUpdateDTO);

        return (int) commitScore;
    }

    // 2. 전공 점수 계산 로직
    public int calculateMajorScore(TeamBuildingDTO teamBuildingDTO) throws IOException {
        User user = userRepository.findById(teamBuildingDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));

        return user.getProjectMemberMajorYn()==YnType.Y?5:0;
    }

    // 3. 경력 점수 계산 로직
    public int calculateCareerScore(TeamBuildingDTO teamBuildingDTO) throws IOException {
        User user = userRepository.findById(teamBuildingDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("프로젝트 정보가 존재하지 않습니다."));
        long careerMonth = user.getUserCareerYears()*12 + user.getUserCareerMonths();
        int careerScore;
        if(careerMonth<6){
            careerScore = 0;
        }
        else if(careerMonth<12){
            careerScore = 1;
        }
        else if(careerMonth<18){
            careerScore = 2;
        }
        else if(careerMonth<36){
            careerScore = 3;
        }
        else if(careerMonth<60){
            careerScore = 4;
        }
        else{
            careerScore = 5;
        }
        log.info("반환 전 경력 점수: "+careerScore);
        return careerScore;

    }

    // 4. 팀원 평가 점수 계산 로직
    public double calculateTeamEvaluation(TeamBuildingDTO teamBuildingDTO) throws IOException {
        List<TeamReview> reviews = teamReviewRepository.findAllByReceiveMemberSeq(teamBuildingDTO.getUserSeq());

        double totalEvaluation = 0;

        if (reviews.isEmpty()) {
            return 3.0; // 평가가 없을 경우 3.0점 반환
        }

        for(TeamReview teamReview: reviews){
            totalEvaluation += teamReview.getReviewStar();
        }
        return totalEvaluation/reviews.size();

    }
    //5. 강사 평가 점수 계산 로직

    public double calculateMentorEvaluation(TeamBuildingDTO teamBuildingDTO) throws IOException{
        List<MentorReview> reviews = projectMentorReviewRepository.findAllByProjectMemberSeq(teamBuildingDTO.getProjectMemberSeq());

        double totalEvaluation = 0;

        if (reviews.isEmpty()) {
            return 3.0; // 평가가 없을 경우 3.0점 반환
        }

        for(MentorReview mentorReview: reviews){
            totalEvaluation += mentorReview.getMentorReviewStar();
        }
        return totalEvaluation/reviews.size();
    }

    // 팀 빌딩 점수 합 구하기.
    public double calculateTotalScore(TeamBuildingDTO teamBuildingDTO, TeamBuildingRuleDTO buildingRuleDTO) throws IOException {
        //
        int commitScore = calculateCommitScore(teamBuildingDTO) * buildingRuleDTO.getRuleGithubWeight();
        int majorScore = calculateMajorScore(teamBuildingDTO) * buildingRuleDTO.getRuleMajorWeight();
        int careerScore = calculateCareerScore(teamBuildingDTO) * buildingRuleDTO.getRuleCareerWeight();
        double teamEvaluationScore = calculateTeamEvaluation(teamBuildingDTO)*buildingRuleDTO.getRuleTeamReviewWeight();
        double mentorEvaluationScore = calculateMentorEvaluation(teamBuildingDTO)*buildingRuleDTO.getRuleMentorReviewWeight();
        return commitScore + majorScore + careerScore+ teamEvaluationScore + mentorEvaluationScore;
    }
    @Transactional
    public List<TeamBuildingResultDTO> buildBalancedTeams(Long projectSeq, int teamBuildingRuleSeq) throws IOException {

        List<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectSeq(projectSeq);
        TeamBuildingRule buildingRule = buildingRuleRepository.findById(teamBuildingRuleSeq)
                .orElseThrow(() -> new RuntimeException("빌딩 규칙이 존재하지 않습니다."));

        List<TeamBuildingDTO> frontMembers = new ArrayList<>();
        List<TeamBuildingDTO> backMembers = new ArrayList<>();

        for (ProjectMember pjMember : projectMembers) {
            if(pjMember.getProjectMentorYn() == YnType.Y){
                continue;
            }

            Long userSeq = pjMember.getUserSeq();

            TeamBuildingDTO teamBuildingDTO = new TeamBuildingDTO(userSeq, pjMember.getProjectMemberSeq());

            double totalScore = calculateTotalScore(teamBuildingDTO, buildingRule.toDTO());
            teamBuildingDTO.setTotalScore(totalScore);

            if (pjMember.getProjectMemberDevelopType().equals(DevelopType.FRONTEND)) {
                frontMembers.add(teamBuildingDTO);
            } else if (pjMember.getProjectMemberDevelopType().equals(DevelopType.BACKEND)) {
                backMembers.add(teamBuildingDTO);
            }
        }

        //3. Fetch the number of teams based on the rules
        int teamCnt = buildingRule.getRuleTeamCount();

        //4. Prepare to group teams and members into result DTOs
        List<TeamBuildingResultDTO> teamBuildingResults = new ArrayList<>();
        Map<String, List<TeamMemberScoreDTO>> teams = new HashMap<>();

        // Initialize teams with their names and empty member lists
        for (int i = 0; i < teamCnt; i++) {
            String teamName = (i + 1) + "조";
            teams.put(teamName, new ArrayList<>());
            teamBuildingResults.add(new TeamBuildingResultDTO(teamName, teams.get(teamName)));
        }

        // 5. Sort members by total score and distribute across teams
        frontMembers.sort(Comparator.comparingDouble(TeamBuildingDTO::getTotalScore).reversed());
        backMembers.sort(Comparator.comparingDouble(TeamBuildingDTO::getTotalScore).reversed());

        assignMembersToTeams(frontMembers, teams);
        assignMembersToTeams(backMembers, teams);

        return teamBuildingResults;
    }

    private void assignMembersToTeams(List<TeamBuildingDTO> members, Map<String, List<TeamMemberScoreDTO>> teams) throws IOException {
        List<String> teamNames = new ArrayList<>(teams.keySet());

        int teamIndex = 0;
        for (TeamBuildingDTO member : members) {
            // Rotate between teams to balance members
            String targetTeamName = teamNames.get(teamIndex % teamNames.size());

            User user = userRepository.findById(member.getUserSeq()).orElseThrow(() -> new RuntimeException("User not found"));

            ProjectMember pjMember = projectMemberRepository.findById(member.getProjectMemberSeq())
                    .orElseThrow(() -> new RuntimeException("프로젝트 멤버 정보가 존재하지 않습니다."));

            // Create TeamMemberScoreDTO and add to the team
            TeamMemberScoreDTO teamMemberScore = new TeamMemberScoreDTO(
                    member.getUserSeq(),
                    user.getUserName(),
                    calculateCommitScore(member),
                    calculateCareerScore(member),
                    calculateMajorScore(member),
                    calculateTeamEvaluation(member),
                    calculateMentorEvaluation(member),
                    member.getTotalScore(),
                    pjMember.getProjectMemberDevelopType()

            );

            teams.get(targetTeamName).add(teamMemberScore);
            teamIndex++;
        }
    }



}

