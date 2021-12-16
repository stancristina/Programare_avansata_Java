package demo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "speaker_id", referencedColumnName = "id")
    private Person speaker;

    @ManyToMany(mappedBy = "tracks")
    private Set<Person> attendees;

    @ManyToOne()
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIgnore
    private Room room;

    public Track() {

    }

    public Track(String title, String description, Person speaker) {
        this.title = title;
        this.description = description;
        this.speaker = speaker;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Person getSpeaker() {
        return speaker;
    }

    public Set<Person> getAttendees() {
        return attendees;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void update(Track track) {
        this.title = track.getTitle();
        this.description = track.getDescription();
        this.speaker = track.getSpeaker();
    }
}
