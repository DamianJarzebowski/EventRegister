package dj.eventregister.eventrecord;

import dj.eventregister.event.Event;
import dj.eventregister.event.EventReadDto;
import dj.eventregister.event.EventReadMapper;
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
    private final EventReadMapper eventMapper;

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
                new InvalidEventRecordException("Brak eventu z id: " + eventId)));
        eventRecord.setParticipant(participant.orElseThrow(() ->
                new InvalidEventRecordException("Brak uczestnika z id " + participantId)));

        return eventRecordMapper.toDto(eventRecordRepository.save(eventRecord));
    }

    EventReadDto updateEventCurrentParticipants(EventRecordDto eventRecordDto) {
        EventRecord eventRecord = eventRecordMapper.toEntity(eventRecordDto);
        Event event = eventRecord.getEvent();
        event.setCurrentParticipants(event.getCurrentParticipants() + 1);
        /*
        if (event.getCurrentParticipants() > event.getMaxParticipant())
            new InvalidEventRecordException();

         */
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

}
