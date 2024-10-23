package sansam.team.team.command.application.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sansam.team.common.aggregate.entity.BaseTimeEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamMemberScheduleDTO extends BaseTimeEntity {

    private final long teamScheduleProgressSeq;

    private final long teamScheduleSeq;

    private final long teamMemberSeq;

    private final String memberScheduleContent;

    private final String memberSchedulePercent;

    private final String memberScheduleFeedback;

}
