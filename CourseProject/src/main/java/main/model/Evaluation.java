package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "evaluation")
public class Evaluation implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = Question.class, mappedBy = "evaluation")
    private Set<Question> questions = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "fk_course")
    @JsonIgnore
    private Course course;

    public Evaluation(Long id) {
        this.id = id;
    }

    public Evaluation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
