package sansam.team.team.query.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import sansam.team.common.websocket.WebSocketClient;
import sansam.team.common.websocket.dto.TeamChatMemberDTO;
import sansam.team.common.websocket.dto.TeamChatMessageDTO;
import sansam.team.security.util.SecurityUtil;
import sansam.team.team.query.dto.*;
import sansam.team.team.query.mapper.TeamChatQueryMapper;
import sansam.team.user.query.dto.CustomUserDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamChatQueryService {
    private final TeamChatQueryMapper teamChatQueryMapper;
    private final MongoTemplate mongoTemplate;

    public List<TeamChatResponse> selectChatRoomList() {
        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();
        return teamChatQueryMapper.selectChatRoomList(new TeamChatRequest(user.getUserSeq(), user.getUserAuth()));
    }

    public TeamChatRoomResponse selectChatRoom(Long teamChatSeq) {
        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();
        TeamChatRoomResponse response = teamChatQueryMapper.selectChatRoom(new TeamChatRoomRequest(teamChatSeq, user.getUserSeq(), user.getUserAuth()));

        Query query = new Query(Criteria.where("teamChatSeq")
                .is(teamChatSeq)
                .and("teamMemberSeq")
                .is(response.getTeamMemberSeq()));
        TeamChatMemberDTO teamChatMember = mongoTemplate.findOne(query, TeamChatMemberDTO.class, "chatMember");

        /*if(teamChatMember == null) {
            teamChatMember = new TeamChatMemberDTO(response.getTeamChatSeq(), response.getTeamMemberSeq(), response.getUserNickname());
            mongoTemplate.insert(teamChatMember);
        }*/

        query = new Query(Criteria.where("teamChatSeq").is(teamChatSeq));

        List<TeamChatMessageDTO> teamChatMessageList = mongoTemplate.find(query, TeamChatMessageDTO.class, "teamChatMessageDTO");
        List<TeamChatMemberDTO> teamChatMemberList = mongoTemplate.find(query, TeamChatMemberDTO.class, "chatMember");

        response.setTeamChatMessageList(teamChatMessageList);
        response.setTeamChatMemberList(teamChatMemberList);
        response.setTeamChatMember(teamChatMember);

        return response;
    }

    public TeamChatMemberResponse selectTeamMember(Long teamMemberSeq) {
        TeamChatMemberResponse response = teamChatQueryMapper.selectTeamMember(teamMemberSeq);
        return response;
    }
}
