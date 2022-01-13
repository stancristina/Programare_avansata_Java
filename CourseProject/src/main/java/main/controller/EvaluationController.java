package main.controller;

import main.model.Evaluation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.EvaluationService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EvaluationController {

    private final Logger log = LoggerFactory.getLogger(EvaluationController.class);

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping(path = "/evaluations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Evaluation>> getEvaluations() {
        log.debug("REST request to get all Evaluations");
        Collection<Evaluation> evaluations = evaluationService.getEvaluations();
        return evaluations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(evaluations);
    }

    @GetMapping(value = "/evaluations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Evaluation> getEvaluation(@PathVariable("id") long id) {
        log.debug("REST request to get Evaluation");
        Optional<Evaluation> evaluation = evaluationService.getEvaluation(id);
        return evaluation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/evaluations")
    public ResponseEntity<Void> addEvaluation(@RequestBody Evaluation evaluation, @RequestParam Long courseId) {
        log.debug("REST request to add Evaluation");
        boolean isSuccessful = evaluationService.addEvaluation(evaluation, courseId);
        if(!isSuccessful) {
            return ResponseEntity.badRequest().build();
        }
        URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash("evaluations").slash(evaluation.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/evaluations/{id}")
    public ResponseEntity<Void> changeEvaluation(@PathVariable("id") Long id, @RequestBody Evaluation evaluation) {
        log.debug("REST request to update Evaluation");
        if (evaluationService.updateEvaluation(evaluation, id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/evaluations/{id}")
    public ResponseEntity<Void> removeEvaluation(@PathVariable("id") Long id) {
        log.debug("REST request to delete Evaluation");
        Optional<Evaluation> existingEvaluation = evaluationService.getEvaluation(id);
        if (existingEvaluation.isPresent()) {
            evaluationService.deleteEvaluation(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
