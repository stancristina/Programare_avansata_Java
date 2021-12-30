package main.service;

import main.model.Chapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.ChapterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterService {

    private final Logger log = LoggerFactory.getLogger(ChapterService.class);

    @Autowired
    private ChapterRepository chapterRepository;

    public List<Chapter> getChapters() {
        log.debug("Request to get all Chapters");
        return chapterRepository.findAll();
    }

    public Optional<Chapter> getChapter(Long id) {
        log.debug("Request to get Chapter");
        return chapterRepository.findById(id);
    }

    public Chapter addChapter(Chapter chapter) {
        log.debug("Request to add Chapter");
        chapterRepository.save(chapter);
        return chapter;
    }

    public boolean updateChapter(Chapter chapter, Long id) {
        log.debug("Request to update Chapter");
        if (chapterRepository.findById(id).isPresent()) {
            chapter.setId(id);
            chapterRepository.save(chapter);
            return true;
        } else {
            return false;
        }
    }

    public boolean patchChapter(Chapter chapter, Long id) {
        log.debug("Request to patch Chapter");
        Optional<Chapter> existingChapter = chapterRepository.findById(id);
        if (existingChapter.isPresent()) {
            existingChapter.get().patch(chapter);
            chapterRepository.save(existingChapter.get());
            return true;
        } else {
            return false;
        }
    }

    public void deleteChapter(Long id) {
        log.debug("Request to delete Chapter : {}", id);
        chapterRepository.deleteById(id);
    }
}
