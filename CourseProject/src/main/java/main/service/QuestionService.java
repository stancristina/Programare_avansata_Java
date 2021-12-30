package main.service;

import main.model.Question;
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

    public List<Question> getQuestions() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestion(Long id) {
        log.debug("Request to get Question");
        return questionRepository.findById(id);
    }

    public Question addQuestion(Question question) {
        log.debug("Request to add Question");
        questionRepository.save(question);
        return question;
    }

    public boolean updateQuestion(Question question, Long id) {
        log.debug("Request to update Question");
        if (questionRepository.findById(id).isPresent()) {
            question.setId(id);
            questionRepository.save(question);
            return true;
        } else {
            return false;
        }
    }

    public boolean patchQuestion(Question question, Long id) {
        log.debug("Request to patch Question");
        Optional<Question> existingQuestion = questionRepository.findById(id);
        if (existingQuestion.isPresent()) {
            existingQuestion.get().patch(question);
            questionRepository.save(existingQuestion.get());
            return true;
        } else {
            return false;
        }
    }

    public void deleteLesson(Long id){
        log.debug("Request to delete Question");
        questionRepository.deleteById(id);
    }
}
