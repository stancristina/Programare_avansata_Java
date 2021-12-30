package main.service;

import main.model.Evaluation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.EvaluationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    private final Logger log = LoggerFactory.getLogger(ChapterService.class);

    @Autowired
    private EvaluationRepository evaluationRepository;

    public List<Evaluation> getEvaluations() {
        log.debug("Request to get all Evaluations");
        return evaluationRepository.findAll();
    }

    public Optional<Evaluation> getEvaluation(Long id) {
        log.debug("Request to get Evaluation");
        return evaluationRepository.findById(id);
    }

    public Evaluation addEvaluation(Evaluation evaluation) {
        log.debug("Request to add Evaluation");
        evaluationRepository.save(evaluation);
        return evaluation;
    }

    public boolean updateEvaluation(Evaluation evaluation, Long id) {
        log.debug("Request to update Evaluation");
        if (evaluationRepository.findById(id).isPresent()) {
            evaluation.setId(id);
            evaluationRepository.save(evaluation);
            return true;
        } else {
            return false;
        }
    }

    public void deleteEvaluation(Long id) {
        log.debug("Request to delete Evaluation");
        evaluationRepository.deleteById(id);
    }
}
