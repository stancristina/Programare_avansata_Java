package demo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Track.class, fetch = FetchType.LAZY, mappedBy = "room")
    private Set<Track> tracks;

    public Room() {

    }

    public Room(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) { tracks.add(track);}

    public void removeTrack(Track track) {
        tracks.remove(track);
    }

    public void update(Room room) {
        this.name = room.getName();
    }
}
