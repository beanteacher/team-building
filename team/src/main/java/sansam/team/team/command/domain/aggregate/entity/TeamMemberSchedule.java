package sansam.team.team.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sansam.team.common.aggregate.entity.BaseTimeEntity;

@Entity @Getter
@Table(name = "tbl_team_member_schedule_progress")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMemberSchedule extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_schedule_progress_seq")
    private long teamScheduleProgressSeq;

    @Column(name = "team_schedule_seq")
    private long teamScheduleSeq;

    @Column(name = "team_member_seq")
    private long teamMemberSeq;

    @Column(name = "team_schedule_progress_content")
    private String memberScheduleContent;

    @Column(name = "team_schedule_progress_percent")
    private String memberSchedulePercent;

    @Column(name = "team_schedule_progress_feedback")
    private String memberScheduleFeedback;

    public void updateMemberSchedule(String memberScheduleContent, String memberSchedulePercent) {
        this.memberScheduleContent = memberScheduleContent;
        this.memberSchedulePercent = memberSchedulePercent;
    }

    public void feedbackMemberSchedule(String memberScheduleContent, String memberSchedulePercent, String memberScheduleFeedback) {
        this.memberScheduleContent = memberScheduleContent;
        this.memberSchedulePercent = memberSchedulePercent;
        this.memberScheduleFeedback = memberScheduleFeedback;
    }

}
