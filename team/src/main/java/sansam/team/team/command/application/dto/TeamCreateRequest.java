package sansam.team.team.command.application.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamCreateRequest {

    private Long projectSeq;
    private List<TeamDTO> teams;
}
