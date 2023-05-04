package dj.eventregister.models.event;

import dj.eventregister.exceptions.ErrorMessage;
import dj.eventregister.exceptions.notFound.NotFoundException;
import dj.eventregister.models.event.dto.EventReadDto;
import dj.eventregister.models.event.dto.EventWriteDto;
import dj.eventregister.models.event.interfaces.EventService;
import dj.eventregister.models.event.interfaces.EventStateMachine;
import dj.eventregister.models.event.mapper.EventReadMapper;
import dj.eventregister.models.event.mapper.EventWriteMapper;
import dj.eventregister.repository.CategoryRepository;
import dj.eventregister.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventServiceImpl implements EventService, EventStateMachine {

    private final EventRepository eventRepository;
    private final EventReadMapper eventReadMapper;
    private final EventWriteMapper eventWriteMapper;
    private final CategoryRepository categoryRepository;

    public List<EventReadDto> findAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventReadMapper::toDto)
                .toList();
    }

    public List<EventReadDto> findAllEventsWithThisCategoryName(String category) {
        return eventRepository.findAll()
                .stream()
                .map(eventReadMapper::toDto)
                .filter(it -> it.getCategory().equals(category))
                .toList();
    }

    public Optional<EventReadDto> findById(Long id) {
        return eventRepository.findById(id)
                .map(eventReadMapper::toDto);
    }

    public EventReadDto save(EventWriteDto dto) {
        findCategoryOrThrowException(dto);
        return eventReadMapper.toDto
                (eventRepository.save
                        (eventWriteMapper.toEntity(dto)
                                .setStateEvent(Event.EventStateMachine.NOT_ENOUGH_PARTICIPANT)));
    }

    public void findCategoryOrThrowException(EventWriteDto dto) {
        var categoryName = dto.getCategory();
        var foundCategory = categoryRepository.findByName(categoryName).orElseThrow(
                () -> {
                    log.error("Category with name: {} does not exists", categoryName);
                    return new NotFoundException((ErrorMessage.NOT_FOUND));
                });
        log.info("Found category with name: {}", foundCategory);
    }

    @Transactional
    public EventReadDto update(EventWriteDto dto, long id) {
        var actual = eventRepository.findById(id).orElseThrow();
        actual.setName(dto.getName())
                .setDescription(dto.getDescription())
                .setMaxParticipant(dto.getMaxParticipant())
                .setMinParticipant(dto.getMinParticipant())
                .setMajority(dto.isMajority())
                .setDateTime(dto.getDateTime())
                .setCategory(categoryRepository.findByName(dto.getCategory()).orElseThrow())
                .setStateEvent(findActualStateEvent(id));
        return eventReadMapper.toDto(actual);
    }

    public void deleteEvent(Long id) {
        var actual = eventRepository.findById(id).orElseThrow(() -> {
            log.error("Event id: {} does not exists", id);
            return new NotFoundException(ErrorMessage.NOT_FOUND);
        });
        eventRepository.deleteById(actual.getId());
    }

    public Event.EventStateMachine findActualStateEvent(Long id) {
        return findById(id)
                .map(EventReadDto::getStateEvent)
                .orElseThrow(RuntimeException::new);
    }

    public void updateStateEvent(Long id, Long newNumberOfParticipants) {
        var event = eventRepository.findById(id).orElseThrow();
        if (newNumberOfParticipants == event.getMinParticipant())
            event.setStateEvent(Event.EventStateMachine.MINIMUM_PARTICIPANT_ACHIEVED);
        if (newNumberOfParticipants == event.getMaxParticipant())
            event.setStateEvent(Event.EventStateMachine.REGISTRATION_CLOSE);
        eventRepository.save(event);
    }
}
