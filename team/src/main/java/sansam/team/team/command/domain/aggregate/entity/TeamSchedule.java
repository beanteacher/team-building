package sansam.team.team.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sansam.team.common.aggregate.entity.BaseTimeEntity;

import java.time.LocalDateTime;

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

    public void updateSchedule(String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.scheduleContent = content;
        this.scheduleStartDate = startDate;
        this.scheduleEndDate = endDate;
    }

    public void changeTeamSeq(long teamSeq) {
        this.teamSeq = teamSeq;
    }

}
