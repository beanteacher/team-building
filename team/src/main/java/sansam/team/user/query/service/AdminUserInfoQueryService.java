package sansam.team.user.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sansam.team.user.query.dto.AdminUserInfoAllQueryDTO;
import sansam.team.user.query.dto.AdminUserInfoQueryDTO;
import sansam.team.user.query.mapper.AdminUserInfoQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserInfoQueryService {

    private final AdminUserInfoQueryMapper adminUserInfoQueryMapper;

    /* 관리자 회원 전체 조회 */
    public List<AdminUserInfoAllQueryDTO> getAllUserInfo() {
        return adminUserInfoQueryMapper.findAllUserInfo();
    }

    /* 관리자 회원 상세 조회 (userSeq를 기반으로 조회) */
    public AdminUserInfoQueryDTO getUserInfo(Long userSeq){
        return adminUserInfoQueryMapper.findUserInfo(userSeq);
    }
}
