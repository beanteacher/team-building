package sansam.team.team.query.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sansam.team.common.aggregate.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamScheduleQueryDTO extends BaseTimeEntity {
    private long teamScheduleSeq;
    private long teamSeq;
    private String teamScheduleContent;
    private String teamScheduleStartDate;
    private String teamScheduleEndDate;
}
