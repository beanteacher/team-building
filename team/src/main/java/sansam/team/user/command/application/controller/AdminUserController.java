package sansam.team.user.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.user.command.application.dto.AdminUserUpdateRequestDTO;
import sansam.team.user.command.application.service.AdminUserService;
import sansam.team.user.command.domain.aggregate.entity.User;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
@Tag(name = "1-3. Admin UserInfo API", description = "관리자의 유저 관리 페이지")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /*관리자가 회원 정보를 수정*/
    @PutMapping("/{userSeq}")
    @Operation(summary = "관리자 회원 수정", description = "관리자 회원 수정 API")
    public ApiResponse<?> updateUserInfo(
            @PathVariable Long userSeq,
            @RequestBody AdminUserUpdateRequestDTO requestDTO) {

        try {
            // 서비스 호출하여 회원 정보 수정
            User updatedUser = adminUserService.updateAdminUser(userSeq, requestDTO);

            // 성공 응답 반환
            return ResponseUtil.successResponse("User updated successfully").getBody();
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (예외 발생 시)
            return ResponseUtil.failureResponse(e.getMessage(), "USER_SEQ_NULL").getBody();
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseUtil.exceptionResponse(e, "USER_UPDATE_ERROR").getBody();
        }
    }

}
