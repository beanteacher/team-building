package sansam.team.team.command.application.dto;

import lombok.*;

import java.util.List;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TeamCreateResponseDTO {
    private Long teamSeq;
    private String teamName;
}
