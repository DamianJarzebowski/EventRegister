package dj.eventregister.eventrecord;

import dj.eventregister.event.Event;
import dj.eventregister.event.EventDto;
import dj.eventregister.event.EventMapper;
import dj.eventregister.event.EventRepository;
import dj.eventregister.participant.Participant;
import dj.eventregister.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventRecordService {

    private final EventRecordRepository eventRecordRepository;
    private final EventRecordMapper eventRecordMapper;

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final EventMapper eventMapper;

    public List<EventRecordDto> findAllEventsRecords() {
        return eventRecordRepository.findAll()
                .stream()
                .map(eventRecordMapper::toDto)
                .toList();
    }

    EventRecordDto registerTheParticipant(EventRecordDto eventRecordDto) {

        Optional<Event> event = eventRepository.findById(eventRecordDto.getEventId());
        Optional<Participant> participant = participantRepository.findById(eventRecordDto.getParticipantId());

        var eventRecord = new EventRecord();

        Long participantId = eventRecordDto.getParticipantId();
        Long eventId = eventRecordDto.getEventId();

        eventRecord.setEvent(event.orElseThrow(() ->
                new InvalidPartyException("Brak eventu z id: " + eventId)));
        eventRecord.setParticipant(participant.orElseThrow(() ->
                new InvalidPartyException("Brak uczestnika z id " + participantId)));

        Event x = eventRepository.getReferenceById(eventRecord.getEvent().getId());
        x.setCurrentParticipants(x.getCurrentParticipants() + 1);
        eventRepository.save(x);

        return eventRecordMapper.toDto(eventRecordRepository.save(eventRecord));
    }

    EventDto updateEventCurrentParticipants(EventRecordDto eventRecordDto) {
        Event eventToUpdate = eventRepository.getReferenceById(eventRecordDto.getEventId());
        eventToUpdate.setCurrentParticipants(eventToUpdate.getCurrentParticipants() + 1);
        Event eventToSave = eventToUpdate;
        Event savedEvent = eventRepository.save(eventToSave);
        return eventMapper.toDto(savedEvent);
    }

}
