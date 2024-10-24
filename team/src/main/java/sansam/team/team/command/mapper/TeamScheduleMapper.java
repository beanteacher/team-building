package sansam.team.team.command.mapper;

import sansam.team.team.command.application.dto.TeamScheduleDTO;
import sansam.team.team.command.domain.aggregate.entity.TeamSchedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeamScheduleMapper {
    public static TeamSchedule toEntity(Long teamSeq, TeamScheduleDTO request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDate = request.getScheduleStartDate() + " 00:00:00";
        String endDate = request.getScheduleEndDate() + " 23:59:59";

        LocalDateTime startDateParse = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateParse = LocalDateTime.parse(endDate, formatter);

        return TeamSchedule.builder()
                .teamSeq(teamSeq)
                .scheduleContent(request.getScheduleContent())
                .scheduleStartDate(startDateParse)
                .scheduleEndDate(endDateParse)
                .build();
    }
}
