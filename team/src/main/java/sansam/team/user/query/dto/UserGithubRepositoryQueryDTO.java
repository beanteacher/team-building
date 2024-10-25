package sansam.team.user.query.dto;

import lombok.Builder;
import lombok.Data;
import sansam.team.common.aggregate.DevelopType;

@Data
@Builder
public class UserGithubRepositoryQueryDTO {

    private Long userGithubRepositorySeq;
    private String userRepositoryUrl;
    private String userRepositoryName;
    private DevelopType developType;
}
