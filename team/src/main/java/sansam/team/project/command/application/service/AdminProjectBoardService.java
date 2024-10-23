package sansam.team.project.command.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import sansam.team.common.s3.FileUploadUtil;
import sansam.team.project.command.application.dto.AdminProjectApplyMemberDTO;
import sansam.team.project.command.application.dto.AdminProjectBoardCreateDTO;
import sansam.team.project.command.application.dto.AdminProjectBoardUpdateDTO;
import sansam.team.project.command.domain.aggregate.entity.ProjectApplyMember;
import sansam.team.project.command.domain.aggregate.entity.ProjectBoard;
import sansam.team.project.command.domain.repository.ProjectApplyMemberRepository;
import sansam.team.project.command.domain.repository.ProjectBoardRepository;
import sansam.team.security.util.SecurityUtil;
import sansam.team.user.query.dto.CustomUserDTO;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminProjectBoardService {

    private final ProjectBoardRepository projectBoardRepository;
    private final ProjectApplyMemberRepository projectApplyMemberRepository;
    private final ModelMapper modelMapper;
    private final FileUploadUtil fileUploadUtil; // 파일 업로드 유틸리티 추가

    /* 프로젝트 모집글 생성 로직 */
    @Transactional
    public ProjectBoard createProjectBoard(AdminProjectBoardCreateDTO adminProjectBoardCreateDTO) {

        CustomUserDTO user = SecurityUtil.getAuthenticatedUser();

        if(ObjectUtils.isEmpty(user.getUserSeq())){
            throw new IllegalArgumentException("User Seq is null");
        }

        // DTO에 설정된 S3 이미지 URL을 포함한 ProjectBoard 객체 생성
        ProjectBoard projectBoard = modelMapper.map(adminProjectBoardCreateDTO, ProjectBoard.class);
        projectBoard.setProjectBoardAdminSeq(user.getUserSeq());

        projectBoardRepository.save(projectBoard);

        return projectBoard;
    }

    /* 프로젝트 모집글 수정 로직 (이미지 포함) */
    @Transactional
    public ProjectBoard updateProjectBoard(Long projectBoardSeq, AdminProjectBoardUpdateDTO adminProjectBoardUpdateDTO, MultipartFile projectBoardImage) throws IOException {

        // 기존 프로젝트 보드를 찾음
        ProjectBoard projectBoard = projectBoardRepository.findById(projectBoardSeq)
                .orElseThrow(() -> new IllegalArgumentException("Project board not found"));

        // 만약 이미지 파일이 존재한다면 S3에 업로드하고 URL을 DTO에 설정
        if (projectBoardImage != null && !projectBoardImage.isEmpty()) {
            String imageUrl = fileUploadUtil.uploadFile(projectBoardImage);
            adminProjectBoardUpdateDTO.setProjectBoardImgUrlFromS3(imageUrl); // DTO에 이미지 URL 설정
        } else {
            // 이미지가 업로드되지 않은 경우 기존 이미지를 유지
            adminProjectBoardUpdateDTO.setProjectBoardImgUrl(projectBoard.getProjectBoardImgUrl());
        }

        // DTO에서 수정된 데이터를 엔티티에 반영
        modelMapper.map(adminProjectBoardUpdateDTO, projectBoard);

        // 업데이트된 객체 저장 및 반환
        return projectBoard;
    }

    /* 프로젝트 모집글 삭제 로직 */
    @Transactional
    public void deleteProjectBoard(Long projectBoardSeq) {
        /* 완전 삭제로 할지 소프트 삭제로 할지 의논 후 제대로 구현해야 할 듯 */
        projectBoardRepository.deleteById(projectBoardSeq);
    }

    @Transactional
    public ProjectApplyMember updateApplyMemberStatus(Long projectBoardSeq, Long applyMemberSeq, AdminProjectApplyMemberDTO adminProjectApplyMemberDTO) {

        // 신청 회원(ProjectApplyMember) 존재 확인
        ProjectApplyMember applyMember = projectApplyMemberRepository.findById(applyMemberSeq)
                .orElseThrow(() -> new IllegalArgumentException("Apply member not found"));

        // 기존 프로젝트 보드를 찾음
        ProjectBoard projectBoard = projectBoardRepository.findById(projectBoardSeq)
                .orElseThrow(() -> new IllegalArgumentException("Project board not found"));

        applyMember.ApplyMemberStatus(projectBoard.getProjectBoardSeq(), adminProjectApplyMemberDTO);

        // 저장
        return applyMember;
    }
}
