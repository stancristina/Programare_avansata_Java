package main.service;

import main.model.Chapter;
import main.model.Course;
import main.model.Lesson;
import main.repository.ChapterRepository;
import main.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.LessonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final Logger log = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Lesson> getLessons() {
        log.debug("Request to get all Lessons");
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLesson(Long id) {
        log.debug("Request to get Lesson");
        return lessonRepository.findById(id);
    }

    public boolean addLesson(Lesson lesson, long chapterId) {
        log.debug("Request to add all Lesson");
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapterId);
        if(chapterOptional.isEmpty()) {
            return false;
        }

        lesson.setChapter(chapterOptional.get());
        lessonRepository.save(lesson);
        return true;
    }

    public boolean updateLesson(Lesson lesson, Long id) {
        log.debug("Request to update Lesson");
        if (lessonRepository.findById(id).isPresent()) {
            lesson.setId(id);
            lessonRepository.save(lesson);
            return true;
        } else {
            return false;
        }
    }

    public void deleteLesson(Long id) {
        log.debug("Request to delete Lesson");
        lessonRepository.deleteById(id);
    }

    public List<Lesson> getLessonsByChapterId(long chapterId) {
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isEmpty()) {
            return null;
        }

        return lessonRepository.findLessonsByChapter(chapterOptional.get());
    }

    public List<Lesson> getLessonsByCourseFiltered(Long courseId, String filter) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty()) {
            return null;
        }

        Course course = courseOptional.get();
        List<Chapter> chapters = chapterRepository.findChaptersByCourse(course);

        List<Lesson> results = new ArrayList<>();
        chapters.forEach(chapter -> {
                List<Lesson> lessons = lessonRepository.findLessonsByChapterAndTitleContaining(chapter, filter);
                results.addAll(lessons);
            }
        );

        return results;
    }
}
