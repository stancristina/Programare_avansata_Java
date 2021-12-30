package main.controller;

import main.model.Chapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.ChapterService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ChapterController {

    private final Logger log = LoggerFactory.getLogger(ChapterController.class);

    @Autowired
    public ChapterService chapterService;

    @GetMapping(path = "/chapters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Chapter>> getChapters() {
        log.debug("REST request to get all Chapters");
        Collection<Chapter> chapters = chapterService.getChapters();
        if (!chapters.isEmpty()) {
            return ResponseEntity.ok(chapters);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/chapters/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chapter> getChapter(@PathVariable("id") long id) {
        log.debug("REST request to get a Chapter");
        Optional<Chapter> chapter = chapterService.getChapter(id);
        return chapter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/chapters", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> addChapter(String title, String description, String orderNumber, String xp) {
        log.debug("REST request to add Chapter");
        Chapter chapter = new Chapter(title, description, orderNumber, xp);
        chapterService.addChapter(chapter);
        URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash("categories").slash(chapter.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/chapters/{id}")
    public ResponseEntity<Void> updateChapter(@PathVariable("id") long id, @RequestBody Chapter chapter) {
        log.debug("REST request to update Chapter");
        Optional<Chapter> existingChapter = chapterService.getChapter(id);
        if (existingChapter.isPresent()) {
            existingChapter.get().update(chapter);
            chapterService.updateChapter(existingChapter.get(), id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/chapters/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable("id") long id) {
        log.debug("REST request to delete Chapter");
        Optional<Chapter> existingChapter = chapterService.getChapter(id);
        if (existingChapter.isPresent()) {
            chapterService.deleteChapter(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
