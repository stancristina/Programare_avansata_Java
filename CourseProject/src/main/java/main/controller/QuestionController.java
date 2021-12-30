package main.controller;

import main.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.QuestionService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @GetMapping(path = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> getQuestions(Question question) {
        log.debug("REST request to get all Questions");
        Collection<Question> questions = questionService.getQuestions();
        if (!questions.isEmpty()) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(path = "/question/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> getQuestion(@PathVariable("id") Long id) {
        log.debug("REST request to get Question");
        Optional<Question> question = questionService.getQuestion(id);
        return question.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/questions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> addQuestion(String question, String answearA, String answearB, String answearC,
                                            String correctAnswear, Integer questionType) {
        log.debug("REST request to add Question");
        Question question1 = new Question(question, answearA, answearB, answearC, correctAnswear, questionType);
        questionService.addQuestion(question1);
        URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash("questions").slash(question1.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, Question question) {
        log.debug("REST request to update Question");
        if (questionService.updateQuestion(question, id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.debug("REST request to delete Question : {}", id);
        Optional<Question> question = questionService.getQuestion(id);
        if (question.isPresent()) {
            questionService.deleteLesson(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
