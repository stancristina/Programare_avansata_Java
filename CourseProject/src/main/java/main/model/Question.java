package main.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer_a")
    private String answerA;

    @Column(name = "answer_b")
    private String answerB;

    @Column(name = "answer_c")
    private String answerC;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "question_type")
    private Integer questionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "questions", allowSetters = true)
    private Evaluation evaluation;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String correctAnswer, Integer questionType) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public void update(Question questionEntity) {
        if (question != null) {
            question = questionEntity.getQuestion();
            answerA = questionEntity.getAnswerA();
            answerB = questionEntity.getAnswerB();
            answerC = questionEntity.getAnswerC();
            correctAnswer = questionEntity.getCorrectAnswer();
            questionType = questionEntity.getQuestionType();
        }
    }

    public void patch(Question questionEntity) {
        if (question != null) {
            if (questionEntity.getQuestion() != null) {
                question = questionEntity.getQuestion();
            }
            if (questionEntity.getAnswerA() != null) {
                answerA = questionEntity.getAnswerA();
            }
            if (questionEntity.getAnswerB() != null) {
                answerB = questionEntity.getAnswerB();
            }
            if (questionEntity.getAnswerC() != null) {
                answerC = questionEntity.getAnswerC();
            }
            if (questionEntity.getCorrectAnswer() != null) {
                correctAnswer = questionEntity.getCorrectAnswer();
            }
            if (questionEntity.getQuestionType() != null) {
                questionType = questionEntity.getQuestionType();
            }
        }
    }
}
