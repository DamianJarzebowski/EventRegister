package dj.eventregister.party;

import dj.eventregister.event.Event;
import dj.eventregister.participant.Participant;
import lombok.Data;

@Data
public class PartyDto {

    private Long id;
    private Event event;
    private Participant participant;
}
