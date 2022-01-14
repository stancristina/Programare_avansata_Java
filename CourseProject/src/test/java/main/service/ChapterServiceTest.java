package main.service;

import main.model.Chapter;
import main.model.Course;
import main.repository.ChapterRepository;
import main.repository.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ChapterService chapterService;

    @Test
    @DisplayName("Test positive result when course is valid")
    void testAddChapterMethodWhenResultIsPositive() {
        // given
        Chapter chapter = new Chapter("Capitolul 1", "Matematica", "Mate pentru pitici", "2");

        long courseId = 5L;
        Course course = new Course();

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));

        // when
        boolean result = chapterService.addChapter(chapter, courseId);

        // then
        assertTrue(result);
        verify(chapterRepository).save(chapter);
    }

    @Test
    @DisplayName("Test negative result when course is invalid")
    void testAddChapterMethodWhenResultIsNegative() {
        // given
        Chapter chapter = new Chapter("Capitolul 1", "Matematica", "Mate pentru pitici", "2");

        long courseId = 5L;

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.empty());

        // when
        boolean result = chapterService.addChapter(chapter, courseId);

        // then
        assertFalse(result);
        verify(chapterRepository, times(0)).save(chapter);
    }

    @Test
    @DisplayName("Test method for update category when result is true")
    void testUpdateCategoryMethod() {
        // given
        Chapter oldChapter = new Chapter("Chapter2", "Matematica", "Mate pentru pitici", "2");
        Chapter newChapter = new Chapter("Chapter2","Matematica", "Matematia pentru pitici", "3");
        when(chapterRepository.findById(newChapter.getId()))
                .thenReturn(Optional.of(oldChapter));
        when(chapterRepository.save(newChapter))
                .thenReturn(newChapter);

        // when
        boolean result = chapterService.updateChapter(newChapter, newChapter.getId());

        // then
        assertTrue(result);
        verify(chapterRepository).save(newChapter);
    }

    @Test
    @DisplayName("Test method for update category when result is false")
    void testUpdateCategoryMethodWhenResultIsFalse() {
        // given
        Chapter newChapter = new Chapter("Chapter2","Matematica", "Matematia pentru pitici", "3");
        when(chapterRepository.findById(newChapter.getId()))
                .thenReturn(Optional.empty());

        // when
        boolean result = chapterService.updateChapter(newChapter, newChapter.getId());

        // then
        assertFalse(result);
        verify(chapterRepository, times(0)).save(newChapter);
    }

    @Test
    @DisplayName("Test method to get all chapters ")
    void testGetAllEvaluationMethod() {
        //given
        Chapter chapter = new Chapter();
        when(chapterRepository.findAll()).thenReturn(List.of(chapter));

        //when
        List<Chapter> result = chapterService.getChapters();

        //then
        assertNotNull(result);
        verify(chapterRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test method to get chapter ")
    void testGetCategoryMethod() {
        //given
        Long chapterId = 6L;
        Chapter chapter = new Chapter("TitleChapter10", "Romana", "Descrp", "4");
        when(chapterRepository.findById(chapterId)).thenReturn(Optional.of(chapter));

        //when
        Optional<Chapter> result = chapterService.getChapter(chapterId);

        //then
        assertNotNull(result);
        verify(chapterRepository, times(1)).findById(chapterId);
    }

    @Test
    @DisplayName("Test patchChapter when chapter is valid")
    void testPatchChapterWhenChapterIsValid() {
        //given
        Long chapterId = 6L;
        Chapter oldChapter = new Chapter("TitleChapter10", "Romana", "Descrp", "4");
        Chapter newChapter = new Chapter("TitleChapter11", "Romana", "Descrp", "4");

        when(chapterRepository.findById(chapterId)).thenReturn(Optional.of(oldChapter));

        //when
        boolean result = chapterService.patchChapter(newChapter, chapterId);

        //then
        assertTrue(result);
        verify(chapterRepository, times(1)).save(oldChapter);
    }

    @Test
    @DisplayName("Test patchChapter when no chapter with that id is found")
    void testPatchChapterWhenChapterIsInvalid() {
        //given
        Long chapterId = 6L;
        Chapter oldChapter = new Chapter("TitleChapter10", "Romana", "Descrp", "4");
        Chapter newChapter = new Chapter("TitleChapter11", "Romana", "Descrp", "4");

        when(chapterRepository.findById(chapterId)).thenReturn(Optional.empty());

        //when
        boolean result = chapterService.patchChapter(newChapter, chapterId);

        //then
        assertFalse(result);
        verify(chapterRepository, times(0)).save(oldChapter);
    }

    @Test
    @DisplayName("Test deleteChapter happyPath")
    void testDeleteChapter() {
        Long chapterId = 6L;

        chapterService.deleteChapter(chapterId);

        verify(chapterRepository, times(1)).deleteById(chapterId);
    }

    @Test
    @DisplayName("Test get chapters by course when course id is invalid")
    void testGetChaptersByCourseIdWhenCourseIdIsInvalid() {
        Long courseId = 6L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        List<Chapter> result =  chapterService.getChaptersByCourseId(courseId);

        assertNull(result);
    }

    @Test
    @DisplayName("Test get chapters by course")
    void testGetChaptersByCourseId() {
        Long courseId = 6L;
        List<Chapter> chapterList = new ArrayList<>();
        chapterList.add(new Chapter());

        Course course = new Course();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(chapterRepository.findChaptersByCourse(course)).thenReturn(chapterList);

        List<Chapter> result =  chapterService.getChaptersByCourseId(courseId);

        assertEquals(chapterList, result);
    }
}
