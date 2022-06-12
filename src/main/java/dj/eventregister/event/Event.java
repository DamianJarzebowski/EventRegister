package dj.eventregister.event;

import dj.eventregister.event_category.Category;
import dj.eventregister.participant.Participant;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private String date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "event")
    private List<Participant> participants = new ArrayList<>() {
    };
}
