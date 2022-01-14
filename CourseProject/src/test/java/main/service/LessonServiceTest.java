package main.service;

import main.model.*;
import main.repository.ChapterRepository;
import main.repository.CourseRepository;
import main.repository.LessonRepository;
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
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonService lessonService;

    @Test
    @DisplayName("Test method to add lesson if chapter exist")
    void testAddLessonMethodWithValidChapter() {
        // given
        Lesson lesson = new Lesson("Matematici aplicate", "1");

        Long chapterId = 5L;
        Chapter chapter = new Chapter();

        when(chapterRepository.findById(chapterId))
                .thenReturn(Optional.of(chapter));

        // when
        boolean result = lessonService.addLesson(lesson, chapterId);

        // then
        assertTrue(result);
        verify(lessonRepository).save(lesson);
    }

    @Test
    @DisplayName("Test method for update chapter")
    void testUpdateLessonMethod() {
        // given
        Long categoryId = 6L;
        Lesson oldLesson = new Lesson("Mate pentru pitici", "2");
        Lesson newLesson = new Lesson("Matematia pentru pitici", "3");
        when(lessonRepository.findById(categoryId))
                .thenReturn(Optional.of(oldLesson));
        when(lessonRepository.save(newLesson))
                .thenReturn(newLesson);

        // when
        boolean result = lessonService.updateLesson(newLesson, categoryId);

        // then
        verify(lessonRepository).save(newLesson);
    }

    @Test
    @DisplayName("Test method to get all lessons ")
    void testGetAllLessonsMethod() {
        //given
        Long lessonId = 6L;
        Lesson lesson = new Lesson("Romana", "4");
        when(lessonRepository.findAll()).thenReturn(List.of(lesson));

        //when
        List<Lesson> result = lessonService.getLessons();

        //then
        assertNotNull(result);
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test method to get Lesson ")
    void testGetLessonMethod() {
        //given
        Long lessonId = 6L;
        Lesson lesson = new Lesson("Romana", "3");
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        //when
        Optional<Lesson> result = lessonService.getLesson(lessonId);

        //then
        assertNotNull(result);
        verify(lessonRepository, times(1)).findById(lessonId);
    }

    @Test
    @DisplayName("Test getLessonsByChapter when chapterId is invalid")
    void testGetLessonsByChapterWhenChapterIdIsInvalid() {
        Long chapterId = 5L;
        when(chapterRepository.findById(chapterId)).thenReturn(Optional.empty());

        List<Lesson> result = lessonService.getLessonsByChapterId(chapterId);

        assertNull(result);
    }

    @Test
    @DisplayName("Test getLessonsByChapter")
    void testGetLessonsByChapterWhenChapter() {
        Long chapterId = 5L;
        Chapter chapter = new Chapter();
        when(chapterRepository.findById(chapterId)).thenReturn(Optional.of(chapter));
        List<Lesson> lessonsList = new ArrayList<>();
        Lesson l1 = new Lesson();
        lessonsList.add(l1);
        when(lessonRepository.findLessonsByChapter(chapter)).thenReturn(lessonsList);

        List<Lesson> result = lessonService.getLessonsByChapterId(chapterId);

        assertEquals(lessonsList, result);
    }

    @Test
    @DisplayName("Test getLessonsByCourseFiltered when courseId is invalid")
    void testGetLessonsByCourseFilteredWhenCourseIdIsInvalid() {
        Long courseId = 5L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        List<Lesson> lessonList = lessonService.getLessonsByCourseFiltered(courseId, "asd");

        assertNull(lessonList);
    }

    @Test
    @DisplayName("Test getLessonsByCourseFiltered when")
    void testGetLessonsByCourseFiltered() {
        String filter = "asd";
        Long courseId = 5L;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        List<Chapter> chapterList = new ArrayList<>();
        Chapter c1 = new Chapter();
        Chapter c2 = new Chapter();
        chapterList.add(c1);
        chapterList.add(c2);
        when(chapterRepository.findChaptersByCourse(course)).thenReturn(chapterList);

        List<Lesson> lesson1ChapterList = new ArrayList<>();
        Lesson l1 = new Lesson();
        Lesson l2 = new Lesson();
        lesson1ChapterList.add(l1);
        lesson1ChapterList.add(l2);
        when(lessonRepository.findLessonsByChapterAndTitleContaining(c1, filter))
                .thenReturn(lesson1ChapterList);

        List<Lesson> lesson2ChapterList = new ArrayList<>();
        Lesson l3 = new Lesson();
        lesson2ChapterList.add(l3);
        when(lessonRepository.findLessonsByChapterAndTitleContaining(c2, filter))
                .thenReturn(lesson2ChapterList);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.addAll(lesson1ChapterList);
        expectedLessons.addAll(lesson2ChapterList);

        List<Lesson> lessonList = lessonService.getLessonsByCourseFiltered(courseId, filter);

        assertNotNull(lessonList);
        for(int i = 0; i < expectedLessons.size(); i++) {
            Lesson expectedLesson = expectedLessons.get(i);
            Lesson actualLesson = lessonList.get(i);
            assertEquals(expectedLesson, actualLesson);
        }
    }
}

