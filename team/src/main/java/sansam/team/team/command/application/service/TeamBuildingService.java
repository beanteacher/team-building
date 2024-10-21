package sansam.team.team.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.project.command.domain.aggregate.entity.MentorReview;
import sansam.team.project.command.domain.repository.ProjectMentorReviewRepository;
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
import sansam.team.team.command.domain.aggregate.entity.TeamMember;
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
    public long calculateCommitScore(TeamBuildingDTO teamBuildingDTO) throws IOException {
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

        return commitScore;
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
        int careerScore = 0;
        if(careerMonth>=60){
            careerScore = 5;
        }
        else if(careerMonth>=36){
            careerScore = 4;
        }
        else if(careerMonth>=18){
            careerScore = 3;
        }
        else if(careerMonth>=12){
            careerScore = 2;
        }
        else if(careerMonth>=6){
            careerScore = 1;
        }
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
        long commitScore = calculateCommitScore(teamBuildingDTO) * buildingRuleDTO.getRuleGithubWeight();
        int majorScore = calculateMajorScore(teamBuildingDTO) * buildingRuleDTO.getRuleMajorWeight();
        int careerScore = calculateCareerScore(teamBuildingDTO) * buildingRuleDTO.getRuleCareerWeight();
        double teamEvaluationScore = calculateTeamEvaluation(teamBuildingDTO)*buildingRuleDTO.getRuleTeamReviewWeight();
        double mentorEvaluationScore = calculateMentorEvaluation(teamBuildingDTO)*buildingRuleDTO.getRuleMentorReviewWeight();
        return commitScore + majorScore + careerScore+ teamEvaluationScore + mentorEvaluationScore;
    }
    //팀 빌딩 로직 -> 팀 빌딩 규칙 추가해야함
    @Transactional
    public List<Team> buildBalancedTeams(Long projectSeq, int teamBuildingRuleSeq) throws IOException {

        //1. 해당 프로젝트 참여자 List와 팀빌딩 규칙 불러오기
        List<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectSeq(projectSeq);
        TeamBuildingRule buildingRule = buildingRuleRepository.findById(teamBuildingRuleSeq)
                .orElseThrow(() -> new RuntimeException("빌딩 규칙이 존재하지 않습니다."));


        //2. 프로젝트 참여자들을 관심 분야에 따라 분류
        List<TeamBuildingDTO> frontMembers = new ArrayList<>();
        List<TeamBuildingDTO> backMembers = new ArrayList<>();

        for (ProjectMember pjMember : projectMembers) {
            if(pjMember.getProjectMentorYn()==YnType.Y){
                continue;
            }

            Long userSeq = pjMember.getUserSeq();
            TeamBuildingDTO teamBuildingDTO = new TeamBuildingDTO(userSeq,pjMember.getProjectMemberSeq());
            // 프로젝트 참여자 점수 불러오기

            double totalScore = calculateTotalScore(teamBuildingDTO, buildingRule.toDTO());
            log.info("프로젝트 유저 번호: "+pjMember.getUserSeq()+" 총 점수: "+totalScore);
            teamBuildingDTO.setTotalScore(totalScore);


            if (pjMember.getProjectMemberDevelopType().equals(DevelopType.FRONTEND)) {
                frontMembers.add(teamBuildingDTO);
            } else if (pjMember.getProjectMemberDevelopType().equals(DevelopType.BACKEND)) {
                backMembers.add(teamBuildingDTO);
            }
        }
        //3. 팀 빌딩 규칙의 팀 개수에 따라 정하기
        int teamCnt = buildingRule.getRuleTeamCount();

        //4. 팀 만들기
        List<Team> teams = new ArrayList<>();

        Map<Team, Double> teamTotalScores = new HashMap<>();
        Map<Team,Long> teamMemberCnt = new HashMap<>();

        for (int i = 0; i < teamCnt; i++) {
            Team newTeam = Team.create(projectSeq, 1, i + 1 + "조");
            teamRepository.save(newTeam);
            teams.add(newTeam);
            teamTotalScores.put(newTeam,0.0);
            teamMemberCnt.put(newTeam,0L);
        }

        // 5. 프로젝트 참여자 점수에 따라 정렬해 팀원 추가하기.
        frontMembers.sort(Comparator.comparingDouble(TeamBuildingDTO::getTotalScore).reversed());
        backMembers.sort(Comparator.comparingDouble(TeamBuildingDTO::getTotalScore).reversed()); 

        // 6. 팀원 분배
        assignMembersToTeams(frontMembers, teamTotalScores, teamMemberCnt);
        assignMembersToTeams(backMembers, teamTotalScores, teamMemberCnt);
        return teams;
    }

    public void assignMembersToTeams(List<TeamBuildingDTO> members, Map<Team, Double> teamTotalScores, Map<Team, Long> teamMemberCnt) {
        for (TeamBuildingDTO member : members) {
            // 1. 인원수가 가장 적은 팀 -> 점수 작은 팀 순으로 선택
            Team targetTeam = findTargetTeam(teamTotalScores,teamMemberCnt);

            TeamMember teamMember = new TeamMember(member.getUserSeq(),targetTeam.getTeamSeq());

            teamMemberRepository.save(teamMember);

            double updatedScore = teamTotalScores.get(targetTeam) + member.getTotalScore();
            teamTotalScores.put(targetTeam, updatedScore);
            teamMemberCnt.put(targetTeam, teamMemberCnt.get(targetTeam)+1);
        }
    }

    public Team findTargetTeam(Map<Team, Double> teamTotalScores, Map<Team, Long> teamMemberCnt) {
        return teamTotalScores.keySet().stream()
                .min(Comparator.comparingLong(teamMemberCnt::get)
                        .thenComparingDouble(teamTotalScores::get))
                .orElseThrow(() -> new NoSuchElementException("팀이 존재하지 않습니다."));
    }

}

