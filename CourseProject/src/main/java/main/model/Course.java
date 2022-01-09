package main.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "course")
public class Course implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "released")
    private LocalDate released;

    @Column(name = "thumbnailUrl")
    private String thumbnailUrl;

    @OneToOne(mappedBy = "course")
    private Evaluation evaluation;

    @ManyToOne
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    private Category category;

    @OneToMany(targetEntity = main.model.Chapter.class, fetch = FetchType.LAZY, mappedBy = "course")
    private Set<Chapter> chapters = new HashSet<>();

    public Course() {
        id = UUID.randomUUID().getMostSignificantBits();
    }

    public Course(String title, String description, String difficulty, LocalDate released, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.released = released;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = LocalDate.parse(released);
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void update(Course course) {
        if (course != null) {
            title = course.getTitle();
            description = course.getDescription();
            difficulty = course.getDifficulty();
            released = course.getReleased();
            thumbnailUrl = course.getThumbnailUrl();
        }
    }

    public void patch(Course course) {
        if (course != null) {
            if (course.getTitle() != null) {
                title = course.getTitle();
            }

            if (course.getDescription() != null) {
                description = course.getDescription();
            }

            if (course.getDifficulty() != null) {
                difficulty = course.getDifficulty();
            }

            if (course.getReleased() != null) {
                released = course.getReleased();
            }

            if (course.getThumbnailUrl() != null) {
                thumbnailUrl = course.getThumbnailUrl();
            }
        }
    }
}
