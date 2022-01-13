package main.service;

import main.model.Category;
import main.model.Course;
import main.model.Evaluation;
import main.repository.CategoryRepository;
import main.repository.EvaluationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.CourseRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Course> getCourses() {
        log.debug("Request to get all Courses");
        return courseRepository.findAll();
    }

    public Optional<Course> getCourse(Long id) {
        log.debug("Request to get  Course");
        return courseRepository.findById(id);
    }

    public boolean addCourse(Course course, long categoryId) {
        log.debug("Request to add Courses");
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return false;
        }
        course.setCategory(categoryOptional.get());
        courseRepository.save(course);
        return true;
    }

    public boolean updateCourse(Course course, Long id, Long categoryId) {
        log.debug("Request to update Course");
        if (courseRepository.findById(id).isEmpty()) {
            return false;
        }

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return false;
        }

        course.setId(id);
        course.setCategory(categoryOptional.get());
        courseRepository.save(course);
        return true;
    }

    public boolean patchCourse(Course course, Long id) {
        log.debug("Request to patch Course");
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            existingCourse.get().patch(course);
            courseRepository.save(existingCourse.get());
            return true;
        } else {
            return false;
        }
    }

    public void deleteCourse(Long id) throws IOException {
        log.debug("Request to delete Course");
        courseRepository.deleteById(id);
    }

    public Optional<Course> getCourseByEvaluationId(long evaluationId) {
        Optional<Evaluation> evaluationOpt = evaluationRepository.findById(evaluationId);
        if (evaluationOpt.isEmpty()) {
            return Optional.empty();
        }

        Optional<Course> optionalCourse = courseRepository.findById(evaluationOpt.get().getId());
        if (optionalCourse.isEmpty()) {
            return Optional.empty();
        }
        return optionalCourse;
    }

    public List<Course> getCourseByCategoryId(long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return null;
        }

        List<Course> optionalCourse = courseRepository.findCoursesByCategory(categoryOptional.get());
        if (optionalCourse.isEmpty()) {
            return null;
        }
        return optionalCourse;
    }
}
