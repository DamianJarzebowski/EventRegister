package dj.eventregister.eventrecord;

import dj.eventregister.event.EventRepository;
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

    Optional<EventRecordReadDto> findById(long id) {
        return eventRecordRepository.findById(id).map(eventRecordReadMapper::toDto);
    }

    EventRecordReadDto registerTheParticipant(EventRecordWriteDto eventRecordWriteDto) {

        Optional.ofNullable(eventRepository.findById(eventRecordWriteDto.getEventId())
                .orElseThrow(() ->
                        new InvalidEventRecordException("Brak eventu z id: " + eventRecordWriteDto.getEventId())));
        Optional.ofNullable(participantRepository.findById(eventRecordWriteDto.getParticipantId())
                .orElseThrow(() ->
                        new InvalidEventRecordException("Brak uczestnika z id " + eventRecordWriteDto.getParticipantId())));

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

    void deleteEventRecord(long id) {
        eventRecordRepository.deleteById(id);
    }

}
