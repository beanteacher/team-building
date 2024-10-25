package sansam.team.team.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.team.command.application.dto.TeamCreateRequest;
import sansam.team.team.command.application.dto.TeamCreateResponseDTO;
import sansam.team.team.command.application.dto.TeamUpdateRequest;
import sansam.team.team.command.application.service.TeamService;
import sansam.team.team.command.domain.aggregate.entity.Team;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
@Tag(name = "3-3. Team API", description = "팀 API")
public class TeamController {

    private final TeamService teamService;

    @PostMapping()
    @Operation(summary = "팀 추가 ")
    public ApiResponse<List<TeamCreateResponseDTO>> createTeams(@RequestBody TeamCreateRequest teamCreateRequest) {
        // 팀 생성 서비스 호출 후 생성된 팀 리스트 반환
        List<TeamCreateResponseDTO> createdTeams = teamService.createTeams(teamCreateRequest);

        // 생성된 팀 정보를 포함한 응답 반환
        return ResponseUtil.successResponse(createdTeams).getBody();
    }


    @PutMapping("/{teamSeq}")
    @Operation(summary = "팀 이름, 팀 빌딩 규칙 변경")
    public ApiResponse<?> updateTeam(@PathVariable Long teamSeq, @RequestBody TeamUpdateRequest request) {
        Team team = teamService.updateTeam(teamSeq, request);

        return ResponseUtil.successResponse("팀 정보 변경 성공").getBody();
    }

    @DeleteMapping("/{teamSeq}")
    public ApiResponse<?> deleteTeam(@PathVariable Long teamSeq) {
        teamService.deleteTeam(teamSeq);

        return ResponseUtil.successResponse("삭제 성공").getBody();
    }

}
