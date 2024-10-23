package sansam.team.user.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansam.team.common.response.ApiResponse;
import sansam.team.user.query.dto.AdminLoginLogDTO;
import sansam.team.user.query.service.AdminUserQueryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/query/admin/loginLog")
@RequiredArgsConstructor
@Tag(name = "1-1. User API", description = "회원 API")
public class AdminUserQueryController {

    private final AdminUserQueryService adminUserQueryService;

    @GetMapping
    @Operation(summary = "관리자가 로그인 로그 전체 조회")
    public ApiResponse<List<AdminLoginLogDTO>> findAllLoginLog(@RequestBody AdminLoginLogDTO.RequestLoginLogDTO adminLoginLogDTO) {
        List<AdminLoginLogDTO> loginLogList = adminUserQueryService.findAllLoginLog(adminLoginLogDTO);

        return ApiResponse.ofSuccess(loginLogList);
    }


}
