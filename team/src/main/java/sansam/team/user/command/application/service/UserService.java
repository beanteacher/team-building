package sansam.team.user.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.user.command.application.dto.UserDTO;
import sansam.team.user.command.domain.aggregate.entity.User;
import sansam.team.user.command.domain.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public boolean joinProcess(UserDTO.UserJoinDTO userJoinDTO) {
        userJoinDTO.changePasswordEncoder(userJoinDTO.getPassword());
        User user = modelMapper.map(userJoinDTO, User.class);

        boolean isOk = true;
        try {
            userRepository.save(user);
        } catch (Exception e) {
            isOk = false;
            log.error(e.getMessage());
        }

        return isOk;
    }
}