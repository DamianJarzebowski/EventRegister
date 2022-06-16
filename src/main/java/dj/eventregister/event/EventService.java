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

    Optional<EventDto> findById(long id) {
        return  eventRepository.findById(id).map(eventMapper::toDto);
    }


    EventDto save(EventDto eventDto) {
        Optional<Event> eventByName = eventRepository.findByName(eventDto.getName());
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

        return mapAndSaveEvent(eventDto);
    }

    EventDto update(EventDto eventDto) {
        int newCurrentParticipants = eventDto.getCurrentParticipants() + 1;
        eventDto.setCurrentParticipants(newCurrentParticipants);
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
