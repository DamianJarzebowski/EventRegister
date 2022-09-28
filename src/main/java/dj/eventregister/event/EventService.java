package dj.eventregister.event;

import dj.eventregister.category.CategoryRepository;
import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.event.dto.EventWriteDto;
import dj.eventregister.event.mapper.EventReadMapper;
import dj.eventregister.event.mapper.EventWriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventReadMapper eventReadMapper;
    private final EventWriteMapper eventWriteMapper;
    private final CategoryRepository categoryRepository;

    List<EventReadDto> findAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(eventReadMapper::toDto)
                .toList();
    }

    List<EventReadDto> findAllEventsWithThisCategoryName(String category) {
        return eventRepository.findAll()
                .stream()
                .map(eventReadMapper::toDto)
                .filter(it -> it.getCategory().equals(category))
                .toList();
    }

    public Optional<EventReadDto> findById(long id) {
        return eventRepository.findById(id)
                .map(eventReadMapper::toDto);
    }

    EventReadDto save(EventWriteDto dto) {
        findCategoryOrThrowException(dto);
        return mapAndSaveEvent(dto);
    }

    void findCategoryOrThrowException(EventWriteDto dto) {
        categoryRepository.findByName(dto.getCategory()).orElseThrow(IllegalArgumentException::new);
    }

    private EventReadDto mapAndSaveEvent(EventWriteDto dto) {
        Event eventEntity = eventWriteMapper.toEntity(dto);
        eventEntity.setStateEvent(Event.EventStateMachine.NOT_ENOUGH_PARTICIPANT);
        return saveAndMap(eventEntity);
    }

    private EventReadDto saveAndMap(Event event) {
        Event savedEvent = eventRepository.save(event);
        return eventReadMapper.toDto(savedEvent);
    }

    EventReadDto update(EventWriteDto dto, Long id) {
        return mapAndUpgradeEvent(dto, id);
    }

    private EventReadDto mapAndUpgradeEvent(EventWriteDto dto, Long id) {
        Event eventEntity = eventReadMapper.toEntity(dto, id);
        Event.EventStateMachine actualState = findById(id)
                .map(EventReadDto::getStateEvent)
                .orElseThrow(RuntimeException::new);
        eventEntity.setStateEvent(actualState);
        return saveAndMap(eventEntity);
    }

    public void updateStateEvent(Long id, Long newNumberOfParticipants) {
        var event = eventRepository.findById(id).orElseThrow();
        if (newNumberOfParticipants == event.getMinParticipant())
            event.setStateEvent(Event.EventStateMachine.MINIMUM_PARTICIPANT_ACHIEVED);
        if (newNumberOfParticipants == event.getMaxParticipant())
            event.setStateEvent(Event.EventStateMachine.REGISTRATION_CLOSE);
        eventRepository.save(event);
    }

    void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
