package main.controller;

import main.model.Chapter;
import main.model.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.LessonService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LessonController {

    private final Logger log = LoggerFactory.getLogger(LessonController.class);

    @Autowired
    private LessonService lessonService;

    @GetMapping(path = "/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Lesson>> getLessons() {
        log.debug("REST request to get all Lessons");
        Collection<Lesson> lessons = lessonService.getLessons();
        if (!lessons.isEmpty()) {
            return ResponseEntity.ok(lessons);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/lessons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lesson> getLesson(@PathVariable("id") Long id) {
        log.debug("REST request to get Lesson");
        Optional<Lesson> lesson = lessonService.getLesson(id);
        return lesson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/lessons", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> addLesson(String title, String orderNumber, @RequestParam long chapterId) {
        log.debug("REST request to add Lesson");
        Lesson lesson = new Lesson(title, orderNumber);
        boolean isSuccessful = lessonService.addLesson(lesson, chapterId);
        if (!isSuccessful) {
            return ResponseEntity.notFound().build();
        }
        URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash("lessons").slash(lesson.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/lessons/{id}")
    public ResponseEntity<Void> updateLessons(@PathVariable("id") Long id, @RequestBody Lesson lesson) {
        log.debug("REST request to update Lesson");
        if (lessonService.updateLesson(lesson, id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/lessons/{id}")
    public ResponseEntity<Void> deleteLessons(@PathVariable Long id) {
        log.debug("REST request to delete Lesson : {}", id);
        Optional<Lesson> existingLesson = lessonService.getLesson(id);
        if (existingLesson.isPresent()) {
            lessonService.deleteLesson(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lessons/chapter/{chapterId}")
    public ResponseEntity<List<Lesson>> getLessonsByChapterId(@PathVariable long chapterId) {
        log.debug("REST request to get lessons  by chapter id");
        List<Lesson> lessons = lessonService.getLessonsByChapterId(chapterId);
        if (lessons == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(lessons);
        }
    }

    @GetMapping("/lessons/course/{courseId}")
    public ResponseEntity<List<Lesson>> getLessonsByCourseFilter(@PathVariable long courseId, @RequestParam String filter) {
        log.debug("REST request to get lessons  by course filter");
        List<Lesson> lessons = lessonService.getLessonsByCourseFiltered(courseId, filter);
        if (lessons == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(lessons);
        }
    }
}
