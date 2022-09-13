package dj.eventregister.event.dto;

import dj.eventregister.event.Event;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

// Used to read full model event via API

@Data
@Accessors(chain = true)
public class EventReadDto {

    private Long id;
    private String name;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private boolean majority;
    private LocalDateTime dateTime;
    private String category;
    private Event.EventStateMachine stateEvent;
}
