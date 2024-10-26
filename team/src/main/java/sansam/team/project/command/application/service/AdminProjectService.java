package sansam.team.project.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import sansam.team.common.s3.FileUploadUtil;
import sansam.team.security.util.SecurityUtil;
import sansam.team.project.command.application.dto.AdminProjectCreateDTO;
import sansam.team.project.command.application.dto.AdminProjectUpdateDTO;
import sansam.team.project.command.domain.aggregate.entity.Project;
import sansam.team.project.command.domain.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import sansam.team.user.query.dto.CustomUserDTO;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final FileUploadUtil fileUploadUtil;

    /* 프로젝트 생성 로직 */
    @Transactional
    public Project createProject(AdminProjectCreateDTO adminProjectCreateDTO){

        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();

        if(ObjectUtils.isEmpty(user.getUserSeq())){
            throw new IllegalArgumentException("User Seq is null");
        }

        if(!adminProjectCreateDTO.getProjectStartDate().isBefore(adminProjectCreateDTO.getProjectEndDate())) {
            throw new IllegalArgumentException("Project Date Check Error");
        }

        Project project = modelMapper.map(adminProjectCreateDTO, Project.class);
        project.setProjectAdminSeq(user.getUserSeq());

        projectRepository.save(project);

        return project;
    }

    /* 프로젝트 수정 로직 */
    @Transactional
    public Project updateProject(Long projectSeq, AdminProjectUpdateDTO adminProjectUpdateDTO, MultipartFile projectImage) throws IOException {

        // 기존 프로젝트를 찾음
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // 만약 이미지 파일이 존재한다면 S3에 업로드하고 URL을 DTO에 설정
        if (projectImage != null && !projectImage.isEmpty()) {
            String imageUrl = fileUploadUtil.uploadFile(projectImage);
            adminProjectUpdateDTO.setProjectBoardImgUrlFromS3(imageUrl); // DTO에 이미지 URL 설정
        } else {
            // 이미지가 업로드되지 않은 경우 기존 이미지를 유지
            adminProjectUpdateDTO.setProjectImgUrl(project.getProjectImgUrl());
        }

        modelMapper.map(adminProjectUpdateDTO, project);

        return project;
    }

    /* 프로젝트 삭제 로직 */
    @Transactional
    public void deleteProject(Long projectSeq) throws Exception {
        /* 완전 삭제로 할지 소프트 삭제로 할지 의논 후 제대로 구현해야 함 */
        try {
            projectRepository.deleteById(projectSeq);
        } catch (Exception e) {
            throw new Exception("Project DELETE FAIL");
        }
    }
}
