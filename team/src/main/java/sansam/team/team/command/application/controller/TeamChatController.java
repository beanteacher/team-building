package sansam.team.team.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.exception.CustomException;
import sansam.team.team.command.application.dto.TeamChatCreateRequest;
import sansam.team.team.command.application.dto.TeamChatUpdateRequest;
import sansam.team.team.command.application.service.TeamChatService;
import sansam.team.team.command.domain.aggregate.entity.TeamChat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
@Tag(name = "3-5. Team Chat API", description = "팀 채팅방 API")
public class TeamChatController {

    private final TeamChatService teamChatService;

    @PostMapping("/{teamSeq}/chat")
    @Operation(summary = "팀 채팅방 추가")
    public ApiResponse<?> createTeamChat(
            @PathVariable Long teamSeq,
            @RequestBody TeamChatCreateRequest request) {
        try {
            TeamChat teamChat = teamChatService.createTeamChat(teamSeq, request);

            return ResponseUtil.successResponse("Team Chatting Room insert success").getBody();
        } catch (Exception e) {
            return ResponseUtil.exceptionResponse(e, "TEAM_CHAT_INSERT_ERROR").getBody();
        }
    }

    @PutMapping("/{teamSeq}/chat/{teamChatSeq}")
    @Operation(summary = "팀 채팅방 정보 수정")
    public ApiResponse<?> updateTeamChat(
            @PathVariable Long teamSeq,
            @PathVariable Long teamChatSeq,
            @RequestBody TeamChatUpdateRequest request) {
        try {
            TeamChat teamChat = teamChatService.updateTeamChat(teamChatSeq, request);

            return ResponseUtil.successResponse("Team Chatting Room update success").getBody();
        } catch (CustomException e) {
            return ResponseUtil.failureResponse(e.getMessage(), "INVALID_TEAM_CHAT_SEQ").getBody();
        } catch (Exception e) {
            return ResponseUtil.exceptionResponse(e, "TEAM_CHAT_UPDATE_ERROR").getBody();
        }
    }
}
