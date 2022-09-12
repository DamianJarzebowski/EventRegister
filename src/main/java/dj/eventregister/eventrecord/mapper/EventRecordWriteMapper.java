package dj.eventregister.eventrecord.mapper;

import dj.eventregister.event.EventRepository;
import dj.eventregister.eventrecord.EventRecord;
import dj.eventregister.eventrecord.dto.EventRecordWriteDto;
import dj.eventregister.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventRecordWriteMapper {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public EventRecordWriteDto toDto(EventRecord eventRecord) {
        var dto = new EventRecordWriteDto();

        var event = eventRecord.getEvent();
        var participant = eventRecord.getParticipant();

        dto.setEventId(event.getId());
        dto.setParticipantId(participant.getId());

        return dto;
    }

    public EventRecord toEntity(EventRecordWriteDto eventRecordWriteDto) {
        var entity = new EventRecord();

        var event = eventRecordWriteDto.getEventId();
        entity.setEvent(eventRepository.getReferenceById(event));

        var participant = eventRecordWriteDto.getParticipantId();
        entity.setParticipant(participantRepository.getReferenceById(participant));

        return entity;
    }
}
