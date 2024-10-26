package sansam.team.project.command.application.dto;

import lombok.*;
import sansam.team.project.command.domain.aggregate.ProjectStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminProjectCreateDTO {

    private String projectTitle;
    private String projectContent;
    private ProjectStatus projectStatus;
    private int projectHeadCount;
    private String projectImgUrl;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
    private Long projectAdminSeq;
}
