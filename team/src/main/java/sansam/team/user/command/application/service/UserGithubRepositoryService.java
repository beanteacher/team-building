package sansam.team.user.command.application.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import sansam.team.user.command.application.dto.UserGithubRepositoryDTO;
import sansam.team.user.command.domain.aggregate.entity.UserGithubRepository;
import sansam.team.user.command.domain.repository.UserGithubRepositoryRepository;
import sansam.team.user.command.domain.repository.UserRepository;
import sansam.team.user.command.mapper.UserGithubRepositoryMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGithubRepositoryService {
    private final UserGithubRepositoryRepository userGithubRepositoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserGithubRepository createRepository(Long userSeq, @RequestBody UserGithubRepositoryDTO dto) {
        log.info("Dto ; {}", dto);
        UserGithubRepository userGithubRepository = UserGithubRepositoryMapper.toEntity(userSeq,dto);
        userGithubRepositoryRepository.save(userGithubRepository);
        return userGithubRepository;
    }
    @Transactional
    public UserGithubRepository updateRepository(Long userGithubRepositorySeq, UserGithubRepositoryDTO dto) {
        UserGithubRepository userGithubRepository = userGithubRepositoryRepository.findById(userGithubRepositorySeq).orElseThrow();
        userGithubRepository.modifyUserGithubRepository(dto.getUserRepositoryUrl(),dto.getUserRepositoryUrl(),dto.getDevelopType());
        return userGithubRepository;
    }

    @Transactional
    public void deleteRepository(Long userGithubRepositorySeq) {
        userGithubRepositoryRepository.deleteById(userGithubRepositorySeq);
    }


}


