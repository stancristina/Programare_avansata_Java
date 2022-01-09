package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "orderNumber")
    private String orderNumber;

    @ManyToOne()
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    @JsonIgnore
    private Chapter chapter;

    public Lesson() {
    }

    public Lesson(String title, String orderNumber) {
        this.title = title;
        this.orderNumber = orderNumber;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void update(Lesson lesson) {
        if (lesson != null) {
            title = lesson.getTitle();
            orderNumber = lesson.getOrderNumber();
        }
    }

    public void patch(Lesson lesson) {
        if (lesson != null) {
            if (lesson.getTitle() != null) {
                title = lesson.getTitle();
            }
            if (lesson.getOrderNumber() != null) {
                orderNumber = lesson.getOrderNumber();
            }
        }
    }
}
