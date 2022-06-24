package dj.eventregister.eventrecord;

import dj.eventregister.event.Event;
import dj.eventregister.event.EventReadDto;
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
    private final EventRecordReadMapper eventRecordReadMapper;
    private final EventRecordWriteMapper eventRecordWriteMapper;

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    List<EventRecordReadDto> findAllEventsRecords() {
        return eventRecordRepository.findAll()
                .stream()
                .map(eventRecordReadMapper::toDto)
                .toList();
    }

    EventRecordReadDto registerTheParticipant(EventRecordWriteDto eventRecordWriteDto) {

        Optional<Event> event = eventRepository.findById(eventRecordWriteDto.getEventId());
        Optional<Participant> participant = participantRepository.findById(eventRecordWriteDto.getParticipantId());

        var eventRecord = new EventRecord();

        Long participantId = eventRecordWriteDto.getParticipantId();
        Long eventId = eventRecordWriteDto.getEventId();

        eventRecord.setEvent(event.orElseThrow(() ->
                new InvalidEventRecordException("Brak eventu z id: " + eventId)));
        eventRecord.setParticipant(participant.orElseThrow(() ->
                new InvalidEventRecordException("Brak uczestnika z id " + participantId)));

        return mapAndSaveEventRecord(eventRecordWriteDto);
    }

    private EventRecordReadDto mapAndSaveEventRecord(EventRecordWriteDto eventRecordWriteDto) {
        EventRecord eventRecordEntity = eventRecordWriteMapper.toEntity(eventRecordWriteDto);
        return saveAndMap(eventRecordEntity);
    }

    private EventRecordReadDto saveAndMap(EventRecord eventRecord) {
        eventRecordRepository.save(eventRecord);
        return eventRecordReadMapper.toDto(eventRecord);
    }

    void deleteEventRecord(Long id) {
        eventRecordRepository.deleteById(id);
    }

    Optional<EventRecordReadDto> findById(Long id) {
        return eventRecordRepository.findById(id).map(eventRecordReadMapper::toDto);
    }

}
