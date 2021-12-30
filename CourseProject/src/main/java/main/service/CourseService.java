package main.service;

import main.model.Course;
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

    public List<Course> getCourses() {
        log.debug("Request to get all Courses");
        return courseRepository.findAll();
    }

    public Optional<Course> getCourse(Long id) {
        log.debug("Request to get  Course");
        return courseRepository.findById(id);
    }

    public Course addCourse(Course course) throws IOException {
        log.debug("Request to add Courses");
        courseRepository.save(course);
        return course;
    }

    public boolean updateCourse(Course course, Long id) throws IOException {
        log.debug("Request to update Course");
        if (courseRepository.findById(id).isPresent()) {
            course.setId(id);
            courseRepository.save(course);
            return true;
        } else {
            return false;
        }
    }

    public boolean patchCourse(Course course, Long id) throws IOException {
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
}
