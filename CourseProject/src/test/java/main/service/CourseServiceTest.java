package main.service;

import main.model.Category;
import main.model.Course;
import main.repository.CategoryRepository;
import main.repository.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName("Test method for add ")
    void testAddCourseMethod() {
        // given
        Course course = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");

        long categoryId = 10L;

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        boolean result = courseService.addCourse(course, categoryId);

        // then
        assertFalse(result);
        verify(courseRepository, times(0)).save(course);
    }

    @Test
    @DisplayName("Test method to get all courses ")
    void testGetAllCoursesMethod() {
        //given
        Course course = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");
        when(courseRepository.findAll()).thenReturn(List.of(course));

        //when
        List<Course> result = courseService.getCourses();

        //then
        assertNotNull(result);
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test method to get course ")
    void testGetCourseMethod() {
        //given
        Long courseId = 6L;
        Course course = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        //when
        Optional<Course> result = courseService.getCourse(courseId);

        //then
        assertNotNull(result);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("Test patchCourse when category is valid")
    void testPatchCourseWhenCategoryIsValid() {
        //given
        Long courseId = 6L;
        Course oldCourse = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");
        Course newCourse = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(oldCourse));

        //when
        boolean result = courseService.patchCourse(newCourse, courseId);

        //then
        assertTrue(result);
        verify(courseRepository, times(1)).save(oldCourse);
    }

    @Test
    @DisplayName("Test patchCourse when category is valid")
    void testPatchCourseWhenCategoryIsInvalid() {
        //given
        Long courseId = 6L;
        Course oldCourse = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");
        Course newCourse = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        //when
        boolean result = courseService.patchCourse(newCourse, courseId);

        //then
        assertFalse(result);
        verify(courseRepository, times(0)).save(oldCourse);
    }


    @Test
    @DisplayName("Test delete Course happyPath")
    void testDeleteCourse() throws IOException {
        Long courseId = 6L;

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    @DisplayName("Test method for update course")
    void testUpdateCourseMethod() {
        // given
        Long courseId = 6L;
        Long categoryId = 5L;
        Category category = new Category("Romana", "Descrp");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Course course = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");
        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));

        // when
        boolean result = courseService.updateCourse(course, courseId, categoryId);

        // then
        assertTrue(result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Test method for update course when result is false")
    void testUpdateCourseMethodWhenResultIsFalse() {
        // given
        Long categoryId = 5L;
        Course course = new Course("Mate", "Matematica", "Mate pentru pitici", LocalDate.of(2022, 01, 10), "url");
        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        // when
        boolean result = courseService.updateCourse(course, course.getId(), categoryId);

        // then
        assertFalse(result);
        verify(courseRepository, times(0)).save(course);
    }
}
