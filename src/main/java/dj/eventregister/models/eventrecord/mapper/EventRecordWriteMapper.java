package dj.eventregister.models.eventrecord.mapper;

import dj.eventregister.models.eventrecord.EventRecord;
import dj.eventregister.models.eventrecord.dto.EventRecordWriteDto;
import dj.eventregister.repository.EventRepository;
import dj.eventregister.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventRecordWriteMapper {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public EventRecord toEntity(EventRecordWriteDto dto) {
        var event = dto.getEventId();
        var participant = dto.getParticipantId();
        return new EventRecord()
                .setEvent(eventRepository.getReferenceById(event))
                .setParticipant(participantRepository.getReferenceById(participant));
    }
}
