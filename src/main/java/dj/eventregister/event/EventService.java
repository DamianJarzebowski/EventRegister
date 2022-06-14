package dj.eventregister.event;

import dj.eventregister.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;

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

    EventDto saveEvent(EventDto eventDto) {
        Event event = eventMapper.toEntity(eventDto);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);

    }

}
