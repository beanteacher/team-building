package sansam.team.user.command.application.dto;

import lombok.*;
import sansam.team.common.aggregate.DevelopType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGithubRepositoryDTO {
    
    private String userRepositoryUrl;
    private String userRepositoryName;
    private DevelopType developType;
}
