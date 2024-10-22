package sansam.team.team.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class TeamBuildingResultDTO {
    private String teamName;
    private List<TeamMemberScoreDTO> teamMembers;
}
