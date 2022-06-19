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

    Optional<EventWriteDto> findById(long id) {
        return  eventRepository.findById(id).map(eventWriteMapper::toDto);
    }

    EventReadDto save(EventReadDto eventReadDto) {
        Optional<Event> eventByName = eventRepository.findByName(eventReadDto.getName());
        eventByName.ifPresent(a -> {
            throw new DuplicateEventNameException();
        });
        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */

        return mapAndSaveEvent(eventReadDto);
    }

    EventReadDto updateEvent(EventReadDto eventReadDto) {
        Optional<Event> eventById = eventRepository.findByName(eventReadDto.getName());
        eventById.ifPresent(event -> {
            if(!event.getId().equals(eventReadDto.getId()))
                throw new DuplicateEventNameException();
        });
        return mapAndSaveEvent(eventReadDto);
    }

    EventReadDto mapAndSaveEvent (EventReadDto eventReadDto) {
        Event event = eventReadMapper.toEntity(eventReadDto);
        Event savedEvent = eventRepository.save(event);
        return eventReadMapper.toDto(savedEvent);
    }

    void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

}
