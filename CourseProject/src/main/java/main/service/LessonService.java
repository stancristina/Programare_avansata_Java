package main.service;

import main.model.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.LessonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final Logger log = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getLessons() {
        log.debug("Request to get all Lessons");
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLesson(Long id) {
        log.debug("Request to get Lesson");
        return lessonRepository.findById(id);
    }

    public Lesson addLesson(Lesson lesson) {
        log.debug("Request to add all Lesson");
        lessonRepository.save(lesson);
        return lesson;
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

    public boolean patchLesson(Lesson lesson, Long id) {
        log.debug("Request to patch Lesson");
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        if (existingLesson.isPresent()) {
            existingLesson.get().patch(lesson);
            lessonRepository.save(existingLesson.get());
            return true;
        } else {
            return false;
        }
    }

    public void deleteLesson(Long id) {
        log.debug("Request to delete Lesson");
        lessonRepository.deleteById(id);
    }
}
