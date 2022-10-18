package dj.eventregister.models.event.interfaces;

import dj.eventregister.models.event.Event;

public interface EventStateMachine {

    void updateStateEvent(Long id, Long newNumberOfParticipants);

    Event.EventStateMachine findActualStateEvent(Long id);
}
