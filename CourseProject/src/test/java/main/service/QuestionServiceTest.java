package main.service;

import main.model.Evaluation;
import main.model.Question;
import main.repository.EvaluationRepository;
import main.repository.QuestionRepository;
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
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("Test method for add ")
    void testAddQuestionMethod() {
        // given
        Question question = new Question("In ce an suntem?", "2020", "2021", "2022", "2022", 1);

        Long evaluationId = 1L;
        Evaluation evaluation = new Evaluation();
        when(evaluationRepository.findById(evaluationId))
                .thenReturn(Optional.of(evaluation));

        // when
        boolean result = questionService.addQuestion(question, evaluationId);

        // then
        verify(questionRepository).save(question);
    }

    @Test
    @DisplayName("Test method for update category when result is true")
    void testUpdateQuestionMethod() {
        // given

        Long evaluationId = 6L;
        Long questionId = 5L;
        Question question = new Question("In ce an suntem?", "2019", "2021", "2022", "2022", 1);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        Evaluation evaluation = new Evaluation();
        when(evaluationRepository.findById(evaluationId)).thenReturn(Optional.of(evaluation));

        // when
        boolean result = questionService.updateQuestion(question, questionId, evaluationId);

        // then
        assertTrue(result);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    @DisplayName("Test method for update Question when result is false")
    void testUpdateQuestionMethodWhenResultIsFalse() {
        // given
        Long evaluationId = 6L;
        Question newQuestion = new Question("In ce an suntem?", "2019", "2021", "2022", "2022", 1);
        when(questionRepository.findById(newQuestion.getId()))
                .thenReturn(Optional.empty());

        // when
        boolean result = questionService.updateQuestion(newQuestion, newQuestion.getId(), evaluationId);

        // then
        assertFalse(result);
        verify(questionRepository, times(0)).save(newQuestion);
    }

    @Test
    @DisplayName("Test method to get all Questions ")
    void testGetAllQuestionsMethod() {
        //given
        Question question = new Question("In ce an suntem?", "2019", "2021", "2022", "2022", 1);
        when(questionRepository.findAll()).thenReturn(List.of(question));

        //when
        List<Question> result = questionService.getQuestions();

        //then
        assertNotNull(result);
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test method to get Question ")
    void testGetQuestionMethod() {
        //given
        Long questionId = 6L;
        Question question = new Question("In ce an suntem?", "2019", "2021", "2022", "2022", 1);
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        //when
        Optional<Question> result = questionService.getQuestion(questionId);

        //then
        assertNotNull(result);
        verify(questionRepository, times(1)).findById(questionId);
    }

    @Test
    @DisplayName("Test delete Question happyPath")
    void testDeleteQuestion() {
        Long questionId = 6L;

        questionService.deleteQuestion(questionId);

        verify(questionRepository, times(1)).deleteById(questionId);
    }

}
