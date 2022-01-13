package main.repository;

import main.model.Chapter;
import main.model.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {

    List<Lesson> findAll();

    List<Lesson> findLessonsByChapter(Chapter chapter);

    List<Lesson> findLessonsByChapterAndTitleContaining(Chapter chapter, String filter);
}
