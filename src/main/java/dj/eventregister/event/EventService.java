package dj.eventregister.event;

import dj.eventregister.category.Category;
import dj.eventregister.category.CategoryRepository;
import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.event.dto.EventWriteDto;
import dj.eventregister.event.mapper.EventReadMapper;
import dj.eventregister.event.mapper.EventWriteMapper;
import dj.eventregister.eventrecord.EventRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventReadMapper eventReadMapper;
    private final EventWriteMapper eventWriteMapper;
    private final EventRecordRepository eventRecordRepository;
    private final CategoryRepository categoryRepository;

    List<EventReadDto> findAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(model -> new Tuple2(model, sumParticipants(model)))
                .map(it -> eventReadMapper.toDto(it.event, it.numberOfParticipant))
                .toList();
    }

    List<EventReadDto> findAllEventsWithThisCategoryName(String category) {
        return eventRepository.findAll()
                .stream()
                .map(model -> new Tuple2(model, sumParticipants(model)))
                .map(it -> eventReadMapper.toDto(it.event, it.numberOfParticipant))
                .filter(it -> it.getCategory().equals(category))
                .toList();
    }

    public Optional<EventReadDto> findById(long id) {
        return eventRepository.findById(id)
                .map(model -> new Tuple2(model, sumParticipants(model)))
                .map(it -> eventReadMapper.toDto(it.event, it.numberOfParticipant));
    }

    /**
     * @throws IllegalArgumentException content is invalid
     * @param content
     * @return
     */
    EventReadDto save(EventWriteDto content) {
        Optional<Category> maybeCategory = categoryRepository.findByName(content.getCategory());
        if (maybeCategory.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return mapAndSaveEvent(content);
    }

    EventReadDto updateEvent(EventWriteDto dto, Long id) {
        return mapAndUpgradeEvent(dto, id);
    }

    private EventReadDto mapAndSaveEvent(EventWriteDto dto) {
        Event eventEntity = eventWriteMapper.toEntity(dto);
        return saveAndMap(eventEntity);
    }

    private EventReadDto mapAndUpgradeEvent(EventWriteDto dto, Long id) {
        Event eventEntity = eventReadMapper.toEntity(dto, id);
        return saveAndMap(eventEntity);
    }

    private EventReadDto saveAndMap(Event event) {
        Event savedEvent = eventRepository.save(event);
        return eventReadMapper.toDto(savedEvent, savedEvent.getCurrentParticipants());
    }

    public int sumParticipants(Event event) {
        return eventRecordRepository.findAll()
                .stream()
                .filter(eventRecord -> eventRecord.getEvent().equals(event))
                .toList()
                .size();
    }

    void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Value
    static class Tuple2 {

        Event event;
        int numberOfParticipant;
    }

}
