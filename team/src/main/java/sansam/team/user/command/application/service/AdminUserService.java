package sansam.team.user.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.user.command.application.dto.AdminUserUpdateRequestDTO;
import sansam.team.user.command.domain.aggregate.entity.User;
import sansam.team.user.command.domain.repository.AdminUserRepository;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    /* 관리자 회원 정보 수정 */
    @Transactional
    public User updateAdminUser(Long userSeq, AdminUserUpdateRequestDTO requestDTO){

        User user = adminUserRepository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        user.modifyAdminUser(requestDTO);

        return user;
    }

}
