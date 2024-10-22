package sansam.team.project.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sansam.team.common.response.ApiResponse;
import sansam.team.common.response.ResponseUtil;
import sansam.team.common.s3.FileUploadUtil;
import sansam.team.project.command.application.dto.AdminProjectCreateDTO;
import sansam.team.project.command.application.dto.AdminProjectUpdateDTO;
import sansam.team.project.command.application.service.AdminProjectService;
import sansam.team.project.command.domain.aggregate.entity.MentorReview;
import sansam.team.project.command.domain.aggregate.entity.Project;

@RestController
@RequestMapping("api/v1/admin/project")
@RequiredArgsConstructor
@Tag(name = "2-3. Project Admin API", description = "프로젝트 관리자 API")
public class AdminProjectController {

    private final AdminProjectService adminProjectService;
    private final FileUploadUtil fileUploadUtil;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "프로젝트 추가", description = "프로젝트 추가 API (관리자만 가능)")
    public ApiResponse<?> createProject(
            @RequestPart("adminProjectCreateDTO") AdminProjectCreateDTO projectDTO,
            @RequestPart("projectImage") MultipartFile projectImage) {

        try {
            // 이미지 업로드 후 S3에서 URL 반환
            String imageUrl = fileUploadUtil.uploadFile(projectImage);

            projectDTO.setProjectImgUrl(imageUrl);

            // Project 생성 요청
            Project creatProject = adminProjectService.createProject(projectDTO);

            // 성공 응답 반환
            return ResponseUtil.successResponse("Project created successfully").getBody();
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (예외 발생 시)
            return ResponseUtil.failureResponse(e.getMessage(), "USER_SEQ_NULL").getBody();
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseUtil.exceptionResponse(e, "PROJECT_CREATE_ERROR").getBody();
        }
    }

    @PutMapping("/{projectSeq}")
    @Operation(summary = "프로젝트 수정", description = "프로젝트 수정 API (관리자만 가능)")
    public ApiResponse<?> updateProjectBoard(
            @PathVariable Long projectSeq,
            @RequestBody AdminProjectUpdateDTO adminProjectUpdateDTO) {

        try {
            // Project 수정 요청
            Project updateProject = adminProjectService.updateProject(projectSeq, adminProjectUpdateDTO);

            // 성공 응답 반환
            return ResponseUtil.successResponse("Project updated successfully").getBody();
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (예외 발생 시)
            return ResponseUtil.failureResponse(e.getMessage(), "PROJECT_SEQ_NULL").getBody();
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseUtil.exceptionResponse(e, "PROJECT_UPDATE_ERROR").getBody();
        }
    }

    @DeleteMapping("/{projectSeq}")
    @Operation(summary = "프로젝트 삭제", description = "프로젝트 삭제 API (관리자만 가능), 테스트 용도 삭제 API")
    public ApiResponse<?> deleteProject(@PathVariable Long projectSeq) {


        try {
            // Project 삭제 요청
            adminProjectService.deleteProject(projectSeq);

            // 성공 응답 반환
            return ResponseUtil.successResponse("Project deleted successfully").getBody();
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (예외 발생 시)
            return ResponseUtil.failureResponse(e.getMessage(), "PROJECT_SEQ_NULL").getBody();
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseUtil.exceptionResponse(e, "PROJECT_DELETE_ERROR").getBody();
        }
    }

}
