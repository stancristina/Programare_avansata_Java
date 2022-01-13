package main.repository;

import main.model.Category;
import main.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{

    List<Course> findAll();

    List<Course> findCoursesByCategory(Category category);
}
