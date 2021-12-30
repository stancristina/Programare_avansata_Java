package main.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Course> courses = new HashSet<>();

    public Category() {
        id = UUID.randomUUID().getMostSignificantBits();
    }


    public Category(String title, String description) {
        this.title = title;
        this.description = description;
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

    public void update(Category category) {
        title = category.getTitle();
        description = category.getDescription();
    }

    public void patch(Category category) {
        if (category != null) {
            if (category.getTitle() != null) {
                title = category.getTitle();
            }
            if (category.getDescription() != null) {
                description = category.getDescription();
            }
        }
    }
}
