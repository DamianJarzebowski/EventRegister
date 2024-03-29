package dj.eventregister.models.eventrecord.mapper;

import dj.eventregister.models.eventrecord.EventRecord;
import dj.eventregister.models.eventrecord.dto.EventRecordReadDto;
import dj.eventregister.repository.EventRepository;
import dj.eventregister.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventRecordReadMapper {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public EventRecordReadDto toDto(EventRecord eventRecord) {
        var event = eventRecord.getEvent();
        var participant = eventRecord.getParticipant();
        return new EventRecordReadDto()
                .setId(eventRecord.getId())
                .setEventId(event.getId())
                .setParticipantId(participant.getId());
    }

    public EventRecord toEntity(EventRecordReadDto dto) {
        var event = dto.getEventId();
        var participant = dto.getParticipantId();
        return new EventRecord()
                .setId(dto.getId())
                .setEvent(eventRepository.getReferenceById(event))
                .setParticipant(participantRepository.getReferenceById(participant));
    }
}
