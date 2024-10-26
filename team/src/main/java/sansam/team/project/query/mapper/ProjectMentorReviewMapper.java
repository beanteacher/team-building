package sansam.team.project.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.project.query.dto.ProjectMentorReviewAllQueryDTO;
import sansam.team.project.query.dto.ProjectMentorReviewAllUserQueryDTO;
import sansam.team.project.query.dto.ProjectMentorReviewQueryDTO;
import sansam.team.project.query.dto.ProjectMentorReviewUserQueryDTO;

import java.util.List;

@Mapper
public interface ProjectMentorReviewMapper {
    List<ProjectMentorReviewAllQueryDTO> findAllForMentor();

    ProjectMentorReviewQueryDTO findByMentorAndProjectMember(Long mentorSeq, Long projectMemberSeq);

    ProjectMentorReviewUserQueryDTO findByIdForUser(Long mentorReviewSeq);

    List<ProjectMentorReviewAllUserQueryDTO> findAllByIdForUser();
}
