package sansam.team.user.command.mapper;

import sansam.team.team.command.domain.aggregate.entity.Team;
import sansam.team.user.command.application.dto.UserGithubRepositoryDTO;
import sansam.team.user.command.domain.aggregate.entity.UserGithubRepository;

public class UserGithubRepositoryMapper {
    public static UserGithubRepository toEntity(Long userSeq,UserGithubRepositoryDTO dto) {
        return UserGithubRepository.create(
                userSeq,
                dto.getUserRepositoryUrl(),
                dto.getUserRepositoryName(),
                dto.getDevelopType()
        );
    }
}
