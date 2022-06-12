package dj.eventregister.party;

import dj.eventregister.event.Event;
import dj.eventregister.participant.Participant;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int maxParticipant;
    private int minParticipant;
    private LocalDate date;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "party")
    private List<Participant> participantList = new ArrayList<>();


}
