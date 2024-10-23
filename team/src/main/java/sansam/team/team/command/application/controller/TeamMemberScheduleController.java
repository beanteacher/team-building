package sansam.team.team.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.team.command.application.dto.TeamMemberScheduleDTO;
import sansam.team.team.command.application.service.TeamMemberScheduleService;
import sansam.team.team.command.domain.aggregate.entity.TeamMemberSchedule;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team/{teamSeq}/memberSchedule")
@Tag(name = "3-4-2. TeamMember Schedule API", description = "회원 별 진행상황 API")
public class TeamMemberScheduleController {

    private final TeamMemberScheduleService teamMemberScheduleService;

    @PostMapping
    @Operation(summary = "팀원 진행상황 추가")
    public ApiResponse<String> createScheduleByMember(
            @PathVariable Long teamSeq,
            @RequestBody TeamMemberScheduleDTO memberScheduleDTO) {
        boolean result = teamMemberScheduleService.createScheduleByMember(memberScheduleDTO);

        return ResponseUtil.successResponse(result ? "팀원 진행상황 추가 성공" : "팀원 진행상황 추가 실패").getBody();
    }

    @PutMapping("/{memberScheduleSeq}")
    @Operation(summary = "팀원 진행상황 수정")
    public ApiResponse<String> updateScheduleByMember(
            @PathVariable Long teamSeq,
            @PathVariable long memberScheduleSeq,
            @RequestBody TeamMemberScheduleDTO memberScheduleDTO) {
        teamMemberScheduleService.updateScheduleByMember(memberScheduleSeq, memberScheduleDTO);
        return ResponseUtil.successResponse("팀원 진행상황 수정 성공").getBody();
    }

    @DeleteMapping("/{memberScheduleSeq}")
    @Operation(summary = "팀원 진행상황 삭제")
    public ApiResponse<String> deleteScheduleByMember(
            @PathVariable Long teamSeq,
            @PathVariable long memberScheduleSeq) {
        teamMemberScheduleService.deleteScheduleByMember(memberScheduleSeq);
        return ResponseUtil.successResponse("팀원 진행상황 삭제 성공").getBody();
    }

    @PutMapping("/{memberScheduleSeq}/feedback")
    @Operation(summary = "강사가 팀원 진행상황 피드백")
    public ApiResponse<String> feedbackScheduleByMentor(
            @PathVariable Long teamSeq,
            @PathVariable long memberScheduleSeq,
            @RequestBody TeamMemberScheduleDTO memberScheduleDTO) {
        teamMemberScheduleService.feedbackScheduleByMentor(memberScheduleSeq, memberScheduleDTO);
        return ResponseUtil.successResponse("팀원 진행상황 피드백 성공").getBody();
    }
}
