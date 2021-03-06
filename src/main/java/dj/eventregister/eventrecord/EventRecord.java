package dj.eventregister.eventrecord;

import dj.eventregister.event.Event;
import dj.eventregister.participant.Participant;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

}
