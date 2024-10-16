package sansam.team.user.query.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansam.team.common.response.ApiResponse;
import sansam.team.user.query.dto.AdminUserInfoAllQueryDTO;
import sansam.team.user.query.service.AdminUserInfoQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
@Tag(name = "1-2. Admin UserInfo API", description = "관리자의 유저 조회 페이지")
public class AdminUserInfoQueryController {

    private final AdminUserInfoQueryService adminUserInfoQueryService;

    @GetMapping()
    @Operation(summary = "관리자 유저 정보 전체 조회", description = "관리자 유저 정보 전체 조회 API (관리자만 가능)")
    public ApiResponse<List<AdminUserInfoAllQueryDTO>> getAllUsers() {
        List<AdminUserInfoAllQueryDTO> userInfoList = adminUserInfoQueryService.getAllUserInfo();
        return ApiResponse.ofSuccess(userInfoList);
    }


}
