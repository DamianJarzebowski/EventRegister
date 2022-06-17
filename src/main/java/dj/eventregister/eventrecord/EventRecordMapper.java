package dj.eventregister.eventrecord;

import org.springframework.stereotype.Service;

@Service
public class EventRecordMapper {

    private EventRecordMapper() {}

    static EventRecordDto toDto (EventRecord eventRecord) {
        var dto = new EventRecordDto();
        var event = eventRecord.getEvent();
        var participant = eventRecord.getParticipant();

        dto.setId(eventRecord.getId());
        dto.setEventId(event.getId());
        dto.setParticipantId(participant.getId());

        return dto;
    }

}
