package dj.eventregister.event;

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
                .filter(event -> event.getCategory().equals(category))
                .toList();
    }

    Optional<EventReadDto> findById(long id) {
        return  eventRepository.findById(id).map(eventReadMapper::toDto);
    }

    EventReadDto save(EventWriteDto eventWriteDto) {
        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
        return mapAndSaveEvent(eventWriteDto);
    }

    EventReadDto updateEvent(EventReadDto eventReadDto) {
        return mapAndUpgradeEvent(eventReadDto);
    }

    EventReadDto mapAndSaveEvent (EventWriteDto eventWriteDto) {
        Event eventEntity = eventWriteMapper.toEntity(eventWriteDto);
        return saveAndMap(eventEntity);
    }

    EventReadDto mapAndUpgradeEvent(EventReadDto eventReadDto) {
        Event eventEntity = eventReadMapper.toEntity(eventReadDto);
        return saveAndMap(eventEntity);
    }

    private EventReadDto saveAndMap(Event event) {
        Event savedEvent = eventRepository.save(event);
        return eventReadMapper.toDto(savedEvent);
    }

    void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

}
