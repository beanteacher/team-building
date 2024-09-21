package sansam.team.project.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sansam.team.project.command.domain.aggregate.ApplyStatus;
import sansam.team.project.query.dto.projectboard.ProjectApplyMemberQueryDTO;
import sansam.team.project.query.dto.projectboard.ProjectBoardQueryDTO;
import sansam.team.project.query.mapper.ProjectBoardMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectBoardQueryServiceTest {

    @InjectMocks
    private ProjectBoardQueryService projectBoardQueryService;

    @Mock
    private ProjectBoardMapper projectBoardMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProjectBoards() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ProjectBoardQueryDTO project1 = new ProjectBoardQueryDTO(
                1L, "Project 1", "Content 1", 5, "imgUrl1",
                now, now.plusDays(30), "RECRUITMENT", now, now.plusMonths(1),
                now, now, null, 1L);

        ProjectBoardQueryDTO project2 = new ProjectBoardQueryDTO(
                2L, "Project 2", "Content 2", 10, "imgUrl2",
                now.minusDays(10), now.plusDays(20), "IN_PROGRESS", now.minusMonths(1), now.plusMonths(2),
                now.minusDays(10), now, null, 2L);

        List<ProjectBoardQueryDTO> mockProjectBoards = Arrays.asList(project1, project2);

        when(projectBoardMapper.findAll()).thenReturn(mockProjectBoards);

        // Act
        List<ProjectBoardQueryDTO> result = projectBoardQueryService.getAllProjectBoards();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getProjectBoardTitle());
        assertEquals("Project 2", result.get(1).getProjectBoardTitle());
        verify(projectBoardMapper, times(1)).findAll();
    }

    @Test
    void testGetProjectBoardByIdSuccess() {
        // Arrange
        Long projectBoardSeq = 1L;
        LocalDateTime now = LocalDateTime.now();
        ProjectBoardQueryDTO mockProjectBoard = new ProjectBoardQueryDTO(
                projectBoardSeq, "Project 1", "Content 1", 5, "imgUrl1",
                now, now.plusDays(30), "RECRUITMENT", now, now.plusMonths(1),
                now, now, null, 1L);

        when(projectBoardMapper.findById(projectBoardSeq)).thenReturn(mockProjectBoard);

        // Act
        ProjectBoardQueryDTO result = projectBoardQueryService.getProjectBoardById(projectBoardSeq);

        // Assert
        assertNotNull(result);
        assertEquals("Project 1", result.getProjectBoardTitle());
        assertEquals("Content 1", result.getProjectBoardContent());
        assertEquals(5, result.getProjectBoardHeadCount());
        assertEquals("imgUrl1", result.getProjectBoardImgUrl());
        assertEquals("RECRUITMENT", result.getBoardStatus());
        assertEquals(1L, result.getUserSeq());
        verify(projectBoardMapper, times(1)).findById(projectBoardSeq);
    }

    @Test
    void testGetProjectBoardByIdNotFound() {
        // Arrange
        Long projectBoardSeq = 1L;

        when(projectBoardMapper.findById(projectBoardSeq)).thenReturn(null);

        // Act
        ProjectBoardQueryDTO result = projectBoardQueryService.getProjectBoardById(projectBoardSeq);

        // Assert
        assertNull(result);
        verify(projectBoardMapper, times(1)).findById(projectBoardSeq);
    }

    @Test
    void testFindApplyMembersByProjectBoardSeq() {
        // Arrange
        Long projectBoardSeq = 1L;

        ProjectApplyMemberQueryDTO member1 = new ProjectApplyMemberQueryDTO(
                1L, "John Doe", "johndoe", ApplyStatus.APPLIED);

        ProjectApplyMemberQueryDTO member2 = new ProjectApplyMemberQueryDTO(
                2L, "Jane Smith", "janesmith", ApplyStatus.APPROVED);

        List<ProjectApplyMemberQueryDTO> mockMembers = Arrays.asList(member1, member2);

        when(projectBoardMapper.findApplyMembersByProjectBoardSeq(projectBoardSeq)).thenReturn(mockMembers);

        // Act
        List<ProjectApplyMemberQueryDTO> result = projectBoardQueryService.findApplyMembersByProjectBoardSeq(projectBoardSeq);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getUserName());
        assertEquals(ApplyStatus.APPLIED, result.get(0).getApplyStatus());
        assertEquals("Jane Smith", result.get(1).getUserName());
        assertEquals(ApplyStatus.APPROVED, result.get(1).getApplyStatus());
        verify(projectBoardMapper, times(1)).findApplyMembersByProjectBoardSeq(projectBoardSeq);
    }

    @Test
    void testFindApplyMembersByProjectBoardSeqNoMembers() {
        // Arrange
        Long projectBoardSeq = 1L;

        when(projectBoardMapper.findApplyMembersByProjectBoardSeq(projectBoardSeq)).thenReturn(Arrays.asList());

        // Act
        List<ProjectApplyMemberQueryDTO> result = projectBoardQueryService.findApplyMembersByProjectBoardSeq(projectBoardSeq);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(projectBoardMapper, times(1)).findApplyMembersByProjectBoardSeq(projectBoardSeq);
    }
}
