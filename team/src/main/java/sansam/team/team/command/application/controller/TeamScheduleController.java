package sansam.team.team.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.team.command.application.dto.TeamScheduleDTO;
import sansam.team.team.command.application.service.TeamScheduleService;
import sansam.team.team.command.domain.aggregate.entity.TeamSchedule;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team/{teamSeq}/schedule")
@Tag(name = "3-4-1. Team Schedule API", description = "팀 일정 API")
public class TeamScheduleController {

    private final TeamScheduleService teamScheduleService;

    @PostMapping
    @Operation(summary = "팀 일정 추가")
    public ApiResponse<String> createTeamSchedule(@PathVariable long teamSeq, @RequestBody TeamScheduleDTO scheduleDTO) {
        boolean result = teamScheduleService.createTeamSchedule(teamSeq, scheduleDTO);
        return ResponseUtil.successResponse(result ? "팀 일정 추가 성공" : "팀 일정 추가 실패").getBody();
    }

    @PutMapping("/{scheduleSeq}")
    @Operation(summary = "팀 일정 수정")
    public ApiResponse<String> updateTeamSchedule(@PathVariable long teamSeq, @PathVariable long scheduleSeq, @RequestBody TeamScheduleDTO scheduleDTO) {
        TeamSchedule teamSchedule = teamScheduleService.updateTeamSchedule(teamSeq, scheduleSeq, scheduleDTO);
        return ResponseUtil.successResponse("팀 일정 수정 성공").getBody();
    }

    @DeleteMapping("/{scheduleSeq}")
    @Operation(summary = "팀 일정 삭제")
    public ApiResponse<String> deleteTeamSchedule(@PathVariable long scheduleSeq) {
        teamScheduleService.deleteTeamSchedule(scheduleSeq);
        return ResponseUtil.successResponse("팀 일정 삭제 성공").getBody();
    }
}
