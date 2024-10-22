package sansam.team.team.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sansam.team.common.response.ApiResponse;
import sansam.team.team.query.dto.TeamChatResponse;
import sansam.team.team.query.dto.TeamChatRoomResponse;
import sansam.team.team.query.service.TeamChatQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
@Tag(name = "3-5. Team Chat API", description = "팀 채팅방 API")
public class TeamChatQueryController {

    private final TeamChatQueryService teamChatQueryService;

    @GetMapping("/{teamSeq}/chat")
    @Operation(summary = "팀 채팅방 리스트 조회")
    public ApiResponse<List<TeamChatResponse>> selectChatRoomList() {
        List<TeamChatResponse> chatRooms = teamChatQueryService.selectChatRoomList();
        return ApiResponse.ofSuccess("Chat room list retrieved successfully", chatRooms);
    }

    @GetMapping("/{teamSeq}/chat/{teamChatSeq}")
    @Operation(summary = "팀 채팅방 조회(입장)")
    public ApiResponse<TeamChatRoomResponse> enterChatRoom(@PathVariable Long teamChatSeq) {
        TeamChatRoomResponse chatRoom = teamChatQueryService.selectChatRoom(teamChatSeq);
        return ApiResponse.ofSuccess("Entered chat room successfully", chatRoom);
    }

}
