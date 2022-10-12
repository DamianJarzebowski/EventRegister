package dj.eventregister.models.eventrecord;

import dj.eventregister.models.event.Event;
import dj.eventregister.models.participant.Participant;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
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
