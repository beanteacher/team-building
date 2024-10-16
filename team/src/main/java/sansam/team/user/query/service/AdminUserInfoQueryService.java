package sansam.team.user.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sansam.team.user.query.dto.AdminUserInfoAllQueryDTO;
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

    /* 관리자 회원 상세 조회 */
    /* 이미지URL 이름 아이디 닉네임 이메일 회원유형 상태 가입일 전화번호 생년월일 성별 전공 경력일수 깃허브아이디 참여중인 프로젝트 */
}
