package dj.eventregister.eventrecord;

import dj.eventregister.event.EventRepository;
import dj.eventregister.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventRecordReadMapper {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    EventRecordReadDto toDto(EventRecord eventRecord) {
        var dto = new EventRecordReadDto();
        var event = eventRecord.getEvent();
        var participant = eventRecord.getParticipant();

        dto.setId(eventRecord.getId());
        dto.setEventId(event.getId());
        dto.setParticipantId(participant.getId());

        return dto;
    }

    EventRecord toEntity(EventRecordReadDto eventRecordReadDto) {
        var entity = new EventRecord();
        entity.setId(eventRecordReadDto.getId());
        var event = eventRecordReadDto.getEventId();
        entity.setEvent(eventRepository.getReferenceById(event));
        var participant = eventRecordReadDto.getParticipantId();
        entity.setParticipant(participantRepository.getReferenceById(participant));

        return entity;
    }

}
