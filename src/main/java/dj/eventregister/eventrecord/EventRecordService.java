package dj.eventregister.eventrecord;

import dj.eventregister.event.*;
import dj.eventregister.event.dto.EventReadDto;
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

    EventRecordReadDto registerTheParticipant(EventRecordWriteDto eventRecordWriteDto) {

        Optional.ofNullable(eventRepository.findById(eventRecordWriteDto.getEventId())
                .orElseThrow(() ->
                        new InvalidEventRecordException("Brak eventu z id: " + eventRecordWriteDto.getEventId())));
        Optional.ofNullable(participantRepository.findById(eventRecordWriteDto.getParticipantId())
                .orElseThrow(() ->
                        new InvalidEventRecordException("Brak uczestnika z id " + eventRecordWriteDto.getParticipantId())));
        if (!checkMajorityIfNeed(eventRecordWriteDto))
            throw new InvalidEventRecordException("Uczestnik nie osiągnoł pełnoletności");
        if (!checkLimitParticipantInEvent(eventRecordWriteDto))
            throw  new InvalidEventRecordException("Zapis na event niemożliwy, osiagnięto limit chętnych");
        if (!checkIfTheParticipantIsAlreadyRegistered(eventRecordWriteDto))
            throw new InvalidEventRecordException("Uczestnik o tym id został juz zarejestrowany na ten event");

        return mapAndSaveEventRecord(eventRecordWriteDto);
    }

    private boolean checkIfTheParticipantIsAlreadyRegistered(EventRecordWriteDto eventRecordWriteDto) {
        Long participantId = eventRecordWriteDto.getParticipantId();
        List<EventRecordReadDto> list = findAllEventsRecords()
                .stream()
                .filter(eventRecord -> eventRecord.getParticipantId().equals(participantId))
                .toList();
        return list.isEmpty();
    }

    private boolean checkLimitParticipantInEvent(EventRecordWriteDto eventRecordWriteDto) {
        Long eventId = eventRecordWriteDto.getEventId();
        Optional<EventReadDto> eventReadDto = eventService.findById(eventId);
        int maxParticipants = eventReadDto
                .map(EventReadDto::getMaxParticipant)
                .orElseThrow(RuntimeException::new);
        Optional<Event> event = eventRepository.findById(eventId);
        int currentParticipants = eventService.sumParticipants(event.orElseThrow(RuntimeException::new));
        return maxParticipants > currentParticipants;
    }


    private boolean checkMajorityIfNeed(EventRecordWriteDto eventRecordWriteDto) {
        if (findEventMajority(eventRecordWriteDto)) {
            return checkParticipantMajority(eventRecordWriteDto);
        } else return true;
    }

    private boolean findEventMajority(EventRecordWriteDto eventRecordWriteDto) {
        Long eventId = eventRecordWriteDto.getEventId();
        Optional<Event> event = eventRepository.findById(eventId);
        return event
                .map(Event::isMajority)
                .orElseThrow(RuntimeException::new);
    }

    private int checkParticipantAge(EventRecordWriteDto eventRecordWriteDto) {
        Long participantId = eventRecordWriteDto.getParticipantId();
        Optional<Participant> participant = participantRepository.findById(participantId);
        return participant
                .map(Participant::getAge)
                .orElseThrow(RuntimeException::new);
    }

    private boolean checkParticipantMajority(EventRecordWriteDto eventRecordWriteDto) {
        final int MAX_AGE_OF_UNDERAGE = 17;
        return checkParticipantAge(eventRecordWriteDto) > MAX_AGE_OF_UNDERAGE;
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
