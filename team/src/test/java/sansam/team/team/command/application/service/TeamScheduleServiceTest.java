/*
package sansam.team.team.command.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sansam.team.team.command.application.dto.TeamScheduleDTO;
import sansam.team.team.command.domain.aggregate.entity.TeamSchedule;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TeamScheduleServiceTest {

    TeamScheduleService teamScheduleService;

    @Test
    public void testCreateTeamSchedule_Success() {
        TeamScheduleDTO teamSchedule = new TeamScheduleDTO(1, 1, "성실해요", LocalDateTime.now(), LocalDateTime.now());

        boolean result = teamScheduleService.createTeamSchedule(teamSchedule);

        assertTrue(result);
    }

    @Test
    public void testUpdateTeamSchedule_Success() {
        long scheduleSeq = 1;
        TeamScheduleDTO teamSchedule = new TeamScheduleDTO(1, 1, "성실해요", LocalDateTime.now(), LocalDateTime.now());

        TeamSchedule updatedSchedule = teamScheduleService.updateTeamSchedule(scheduleSeq, teamSchedule);

        assertNotNull(updatedSchedule);
    }

    @Test
    public void testDeleteTeamSchedule_Success() {
        long scheduleSeq = 1;

        teamScheduleService.deleteTeamSchedule(scheduleSeq);

    }

}*/
