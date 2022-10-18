package dj.eventregister.models.event.interfaces;

import dj.eventregister.models.event.dto.EventReadDto;
import dj.eventregister.models.event.dto.EventWriteDto;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<EventReadDto> findAllEvents();

    List<EventReadDto> findAllEventsWithThisCategoryName(String category);

    Optional<EventReadDto> findById(Long id);

    EventReadDto save(EventWriteDto dto);

    EventReadDto update(EventWriteDto dto, Long id);

    void deleteEvent(Long id);
}
