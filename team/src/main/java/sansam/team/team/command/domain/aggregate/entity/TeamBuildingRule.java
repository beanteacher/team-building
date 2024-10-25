package sansam.team.team.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sansam.team.team.command.application.dto.TeamBuildingRuleDTO;
import sansam.team.common.aggregate.YnType;
import sansam.team.common.aggregate.entity.BaseTimeEntity;

@Entity
@Table(name = "tbl_building_rule")
@Getter
@NoArgsConstructor
public class TeamBuildingRule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ruleSeq;
    private int ruleTeamCount;
    private int ruleMajorWeight;
    private int ruleCareerWeight;
    private int ruleGithubWeight;
    private int ruleTeamReviewWeight;
    private int ruleMentorReviewWeight;

    @Enumerated(EnumType.STRING)
    private YnType ruleTechStackYn;

    public TeamBuildingRuleDTO toDTO() {
        TeamBuildingRuleDTO buildingRuleDTO = new TeamBuildingRuleDTO();
        buildingRuleDTO.setRuleTeamCount(this.ruleTeamCount);
        buildingRuleDTO.setRuleMajorWeight(this.ruleMajorWeight);
        buildingRuleDTO.setRuleCareerWeight(this.ruleCareerWeight);
        buildingRuleDTO.setRuleGithubWeight(this.ruleGithubWeight);
        buildingRuleDTO.setRuleTeamReviewWeight(this.ruleTeamReviewWeight);
        buildingRuleDTO.setRuleMentorReviewWeight(this.ruleMentorReviewWeight);
        buildingRuleDTO.setRuleTechStackYn(YnType.valueOf(this.ruleTechStackYn.name())); // Enum을 String으로 변환
        return buildingRuleDTO;
    }

    public void updateFrom(TeamBuildingRuleDTO buildingRuleDTO) {
        this.ruleTeamCount = buildingRuleDTO.getRuleTeamCount();
        this.ruleMajorWeight = buildingRuleDTO.getRuleMajorWeight();
        this.ruleCareerWeight = buildingRuleDTO.getRuleCareerWeight();
        this.ruleGithubWeight = buildingRuleDTO.getRuleGithubWeight();
        this.ruleTeamReviewWeight = buildingRuleDTO.getRuleTeamReviewWeight();
        this.ruleMentorReviewWeight = buildingRuleDTO.getRuleMentorReviewWeight();
        this.ruleTechStackYn = YnType.valueOf(String.valueOf(buildingRuleDTO.getRuleTechStackYn()));
    }

    public Long getTeamBuildingRuleSeq() {
        return ruleSeq;
    }
}
