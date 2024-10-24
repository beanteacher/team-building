package sansam.team.team.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sansam.team.common.aggregate.entity.BaseTimeEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tbl_team_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamSchedule extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_schedule_seq")
    private long teamScheduleSeq;

    @Column(name = "team_seq")
    private long teamSeq;

    @Column(name = "team_schedule_content")
    private String scheduleContent;

    @Column(name = "team_schedule_start_date")
    private LocalDateTime scheduleStartDate;

    @Column(name = "team_schedule_end_date")
    private LocalDateTime scheduleEndDate;

    @Builder
    protected TeamSchedule(long teamSeq, String scheduleContent, LocalDateTime scheduleStartDate, LocalDateTime scheduleEndDate) {
        this.teamSeq = teamSeq;
        this.scheduleContent = scheduleContent;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
    }

    public void updateSchedule(String content, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateParse = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateParse = LocalDateTime.parse(endDate, formatter);

        this.scheduleContent = content;
        this.scheduleStartDate = startDateParse;
        this.scheduleEndDate = endDateParse;
    }

}
