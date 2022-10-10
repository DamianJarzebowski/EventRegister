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

    public EventRecord toEntity(EventRecordWriteDto dto) {
        var event = dto.getEventId();
        var participant = dto.getParticipantId();
        return new EventRecord()
                .setEvent(eventRepository.getReferenceById(event))
                .setParticipant(participantRepository.getReferenceById(participant));
    }
}
