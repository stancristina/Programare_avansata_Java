package main.service;

import main.model.Course;
import main.model.Evaluation;
import main.model.Question;
import main.repository.CourseRepository;
import main.repository.EvaluationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Question> getQuestions() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestion(Long id) {
        log.debug("Request to get Question");
        return questionRepository.findById(id);
    }

    public boolean addQuestion(Question question, Long evaluationId) {
        log.debug("Request to add Question");
        Optional<Evaluation> evaluationOptional = evaluationRepository.findById(evaluationId);
        if (evaluationOptional.isEmpty()) {
            return false;
        }

        question.setEvaluation(evaluationOptional.get());
        questionRepository.save(question);
        return true;
    }

    public boolean updateQuestion(Question question, Long id, Long evaluationId) {
        log.debug("Request to update Question");
        if (questionRepository.findById(id).isEmpty()) {
            return false;
        }

        Optional<Evaluation> evaluationOptional = evaluationRepository.findById(evaluationId);
        if (evaluationOptional.isEmpty()) {
            return false;
        }

        question.setId(id);
        question.setEvaluation(evaluationOptional.get());
        questionRepository.save(question);
        return true;
    }

    public void deleteQuestion(Long id) {
        log.debug("Request to delete Question");
        questionRepository.deleteById(id);
    }

    public List<Question> getQuestionByCourseId(long courseId) {

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return null;
        }
        Optional<Evaluation> evaluationOpt = evaluationRepository.findEvaluationByCourse(courseOpt.get());
        if(evaluationOpt.isEmpty()) {
            return null;
        }

        return questionRepository.findQuestionsByEvaluation(evaluationOpt.get());
    }
}
