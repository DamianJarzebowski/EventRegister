package dj.eventregister.participant;

import dj.eventregister.event.Event;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Participant {

    @Id
    private Long id;
    private String name;
    private String lastName;
    private int age;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;



}
