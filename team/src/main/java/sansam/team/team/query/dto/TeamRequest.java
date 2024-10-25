package sansam.team.team.query.dto;

import lombok.*;
import sansam.team.common.aggregate.RoleType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequest {
    Long userSeq;
    RoleType roleType;
    Long projectSeq;
}
