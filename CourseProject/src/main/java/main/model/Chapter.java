package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chapter")
public class Chapter implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "orderNumber")
    private String orderNumber;

    @Column(name = "xp")
    private String xp;

    @ManyToOne()
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @OneToMany(targetEntity = Lesson.class, fetch = FetchType.LAZY, mappedBy = "chapter")
    private Set<Lesson> lessons = new HashSet<>();

    public Chapter() {

    }

    public Chapter(String title, String description, String orderNumber, String xp) {
        this.title = title;
        this.description = description;
        this.orderNumber = orderNumber;
        this.xp = xp;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }

    public Course getCourseModel() {
        return course;
    }

    public void setCourseModel(Course course) {
        this.course = course;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void update(Chapter chapter) {
        if (chapter != null) {
            title = chapter.getTitle();
            description = chapter.getDescription();
            orderNumber = chapter.getOrderNumber();
            xp = chapter.getXp();
        }
    }

    public void patch(Chapter chapter) {
        if (chapter != null) {
            if (chapter.getTitle() != null) {
                title = chapter.getTitle();
            }
            if (chapter.getDescription() != null) {
                description = chapter.getDescription();
            }
            if (chapter.getOrderNumber() != null) {
                orderNumber = chapter.getOrderNumber();
            }
            if (chapter.getXp() != null) {
                xp = chapter.getXp();
            }
        }
    }
}
