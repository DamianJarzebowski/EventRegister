package dj.eventregister.eventrecord;

import dj.eventregister.event.*;
import dj.eventregister.eventrecord.dto.EventRecordReadDto;
import dj.eventregister.eventrecord.dto.EventRecordWriteDto;
import dj.eventregister.eventrecord.mapper.EventRecordReadMapper;
import dj.eventregister.eventrecord.mapper.EventRecordWriteMapper;
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
    private final EventService eventService;
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

    EventRecordReadDto registerTheParticipant(EventRecordWriteDto dto) {

        var eventId = dto.getEventId();
        var participantId = dto.getParticipantId();

        var event = eventRepository.findById(eventId);
        if(event.isEmpty())
            throw new InvalidEventRecordException("Brak eventu z id: " + eventId);

        var participant = participantRepository.findById(participantId);
        if(participant.isEmpty())
            throw new InvalidEventRecordException("Brak uczestnika z id " + participantId);

        if (!checkMajorityIfNeed(dto))
            throw new InvalidEventRecordException("Uczestnik nie osiągnął pełnoletności");
        if(checkStateEvent(event.get()) == Event.EventStateMachine.REGISTRATION_CLOSE)
            throw new InvalidEventRecordException("Zapis na event niemożliwy, osiagnięto limit chętnych");
        if (!checkIfTheParticipantIsAlreadyRegistered(dto))
            throw new InvalidEventRecordException("Uczestnik o tym id został juz zarejestrowany na ten event");

        var newNumberOfParticipants = eventRecordRepository.countByEventId(eventId) + 1;

        eventService.updateStateEvent(eventId, newNumberOfParticipants);

        return mapAndSaveEventRecord(dto);
    }

    private boolean checkIfTheParticipantIsAlreadyRegistered(EventRecordWriteDto dto) {
        var participantId = dto.getParticipantId();
        var list = findAllEventsRecords()
                .stream()
                .filter(eventRecord -> eventRecord.getParticipantId().equals(participantId))
                .toList();
        return list.isEmpty();
    }

    Event.EventStateMachine checkStateEvent(Event event) {
        return event.getStateEvent();
    }

    private boolean checkMajorityIfNeed(EventRecordWriteDto dto) {
        if (findEventMajority(dto)) {
            return checkParticipantMajority(dto);
        } else return true;
    }

    private boolean findEventMajority(EventRecordWriteDto dto) {
        Long eventId = dto.getEventId();
        Optional<Event> event = eventRepository.findById(eventId);
        return event
                .map(Event::isMajority)
                .orElseThrow(RuntimeException::new);
    }

    private int checkParticipantAge(EventRecordWriteDto dto) {
        Long participantId = dto.getParticipantId();
        Optional<Participant> participant = participantRepository.findById(participantId);
        return participant
                .map(Participant::getAge)
                .orElseThrow(RuntimeException::new);
    }

    private boolean checkParticipantMajority(EventRecordWriteDto dto) {
        final int MAX_AGE_OF_UNDERAGE = 17;
        return checkParticipantAge(dto) > MAX_AGE_OF_UNDERAGE;
    }

    private EventRecordReadDto mapAndSaveEventRecord(EventRecordWriteDto dto) {
        EventRecord eventRecordEntity = eventRecordWriteMapper.toEntity(dto);
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
