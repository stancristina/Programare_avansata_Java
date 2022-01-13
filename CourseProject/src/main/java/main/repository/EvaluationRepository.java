package main.repository;

import main.model.Course;
import main.model.Evaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {

    List<Evaluation> findAll();

    Optional<Evaluation> findEvaluationByCourse(Course course);
}
