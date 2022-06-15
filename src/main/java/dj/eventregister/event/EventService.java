package dj.eventregister.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    List<EventDto> findAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    List<EventDto> findAllEventsWithThisCategoryName(String category) {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toDto)
                .filter(event -> event.getCategory().equals(category))
                .toList();
    }

    EventDto save(EventDto eventDto) {
        Optional<Event> eventByName = eventRepository.findByName(eventDto.getName());
        eventByName.ifPresent(a -> {
            throw new DuplicateEventNameException();
        });
        return mapAndSaveEvent(eventDto);
    }

    EventDto mapAndSaveEvent (EventDto eventDto) {
        Event event = eventMapper.toEntity(eventDto);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

    void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }



}
