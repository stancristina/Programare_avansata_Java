package main.service;

import main.model.Course;
import main.model.Evaluation;
import main.repository.CourseRepository;
import main.repository.EvaluationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluationServiceTest {

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EvaluationService evaluationService;

    @Test
    @DisplayName("Test method returns false when given course does not exist")
    void testAddEvaluationMethodWithInvalidCourse() {
        // given
        Evaluation evaluation = new Evaluation();
        Long courseId = 5L;
        when(courseRepository.findById(courseId))
                .thenReturn(Optional.empty());

        // when
        boolean result = evaluationService.addEvaluation(evaluation, courseId);

        // then
        assertFalse(result);
        verify(evaluationRepository, times(0)).save(evaluation);
    }

    @Test
    @DisplayName("Test method returns true when given course exists")
    void testAddEvaluationMethodWithValidCourse() {
        // given
        Evaluation evaluation = new Evaluation();
        Long courseId = 5L;
        Course course = new Course();

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));

        // when
        boolean result = evaluationService.addEvaluation(evaluation, courseId);

        // then
        assertTrue(result);
        verify(evaluationRepository).save(evaluation);
    }

    @Test
    @DisplayName("Test method returns false when ")
    void testUpdateEvaluationMethod() {
        // given
        Evaluation oldEvaluation = new Evaluation();
        Evaluation newEvaluation = new Evaluation();
        when(evaluationRepository.findById(newEvaluation.getId()))
                .thenReturn(Optional.of(oldEvaluation));
        when(evaluationRepository.save(newEvaluation))
                .thenReturn(newEvaluation);
        // when
        boolean result = evaluationService.updateEvaluation(newEvaluation, newEvaluation.getId());

        // then
        assertTrue(result);
        verify(evaluationRepository).save(newEvaluation);
    }

    @Test
    @DisplayName("Test method returns false when evaluation id is invalid")
    void testUpdateEvaluationMethodWhenEvaluationIdIsInvalid() {
        // given
        Long id = 5L;
        Evaluation newEvaluation = new Evaluation();
        when(evaluationRepository.findById(id))
                .thenReturn(Optional.empty());
        // when
        boolean result = evaluationService.updateEvaluation(newEvaluation, id);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("Test method to get all evaluation ")
    void testGetAllEvaluationMethod() {
        //given
        Evaluation evaluation = new Evaluation();
        when(evaluationRepository.findAll()).thenReturn(List.of(evaluation));

        //when
        List<Evaluation> result = evaluationService.getEvaluations();

        //then
        assertNotNull(result);
        verify(evaluationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test method to get evaluation by id")
    void testGetEvaluationByIdMethod() {
        //given
        Long evaluationId = 5L;
        Evaluation evaluation = new Evaluation(evaluationId);
        when(evaluationRepository.findById(evaluationId)).thenReturn(Optional.of(evaluation));

        //when
        Optional<Evaluation> result = evaluationService.getEvaluation(evaluationId);

        //then
        assertNotNull(result);
        verify(evaluationRepository, times(1)).findById(evaluationId);
    }

    @Test
    @DisplayName("Test delete evaluation happyPath")
    void testDeleteEvaluation() {
        Long evaluationId = 6L;

        evaluationService.deleteEvaluation(evaluationId);

        verify(evaluationRepository, times(1)).deleteById(evaluationId);
    }

}
