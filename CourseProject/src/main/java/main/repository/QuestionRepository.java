package main.repository;

import main.model.Evaluation;
import main.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findAll();

    List<Question> findQuestionsByEvaluation(Evaluation evaluation);
}
