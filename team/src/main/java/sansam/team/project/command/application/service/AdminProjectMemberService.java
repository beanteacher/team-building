package sansam.team.project.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sansam.team.common.aggregate.DevelopType;
import sansam.team.project.command.application.dto.AdminProjectMemberUpdateDTO;
import sansam.team.project.command.domain.aggregate.entity.Project;
import sansam.team.project.command.domain.aggregate.entity.ProjectMember;
import sansam.team.project.command.domain.repository.ProjectMemberRepository;
import sansam.team.project.command.domain.repository.ProjectRepository;
import sansam.team.project.command.mapper.ProjectMemberMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectMember containForProject(Long projectSeq, Long userSeq, DevelopType projectMemberDevelopType) {

        // 주어진 userSeq로 사용자 정보 확인
        if (userSeq == null) {
            throw new IllegalArgumentException("User Seq is null");
        }

        // 프로젝트를 projectSeq로 조회
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // ProjectMember 엔티티 생성
        ProjectMember projectMember = ProjectMemberMapper.toEntity(userSeq, project.getProjectSeq(), projectMemberDevelopType); // Enum 개발 분야 추가
        // adminProjectMemberService 안에서 확인
        log.info("In Service, projectMemberDevelopType: {}", projectMemberDevelopType);

        // 프로젝트 멤버 저장
        projectMemberRepository.save(projectMember);

        return projectMember;
    }


    @Transactional
    public ProjectMember updateProjectMember(Long projectMemberSeq, AdminProjectMemberUpdateDTO updateDTO) {
        // 기존 프로젝트 멤버를 찾음
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberSeq)
                .orElseThrow(() -> new IllegalArgumentException("Project member not found"));

        // 프로젝트 멤버 수정
        projectMember.modifyProjectMember(updateDTO);

        // 업데이트된 프로젝트 멤버 객체를 반환
        return projectMember;
    }

}
