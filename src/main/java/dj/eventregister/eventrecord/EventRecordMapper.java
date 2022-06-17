package dj.eventregister.eventrecord;

import dj.eventregister.event.EventRepository;
import dj.eventregister.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventRecordMapper {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    EventRecordDto toDto(EventRecord eventRecord) {
        var dto = new EventRecordDto();
        var event = eventRecord.getEvent();
        var participant = eventRecord.getParticipant();

        dto.setId(eventRecord.getId());
        dto.setEventId(event.getId());
        dto.setParticipantId(participant.getId());

        return dto;
    }

    EventRecord toEntity(EventRecordDto eventRecordDto) {
        var entity = new EventRecord();
        entity.setId(eventRecordDto.getId());
        var event = eventRecordDto.getEventId();
        entity.setEvent(eventRepository.getReferenceById(event));
        var participant = eventRecordDto.getParticipantId();
        entity.setParticipant(participantRepository.getReferenceById(participant));

        return entity;
    }

}
