package sansam.team.team.query.dto;

import lombok.Data;

@Data
public class TeamMemberScheduleQueryDTO {
    private long teamScheduleSeq;
    private long teamMemberSeq;
    private String teamScheduleProgressContent;
    private String teamScheduleProgressPercent;
    private String teamScheduleProgressFeedback;
    private String regDate;
    private String modDate;
}
