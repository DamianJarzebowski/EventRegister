package dj.eventregister.event.mapper;

import dj.eventregister.category.CategoryRepository;
import dj.eventregister.event.Event;
import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.event.dto.EventWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventReadMapper {

    private final CategoryRepository categoryRepository;

    public EventReadDto toDto(Event event) {
        return new EventReadDto()
                .setId(event.getId())
                .setName(event.getName())
                .setDescription(event.getDescription())
                .setMaxParticipant(event.getMaxParticipant())
                .setMinParticipant(event.getMinParticipant())
                .setMajority(event.isMajority())
                .setDateTime(event.getDateTime())
                .setCategory(event.getCategory().getName())
                .setStateEvent(event.getStateEvent());
    }

    public Event toEntity(EventWriteDto dto, Long id) {
        return new Event()
                .setId(id)
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setMaxParticipant(dto.getMaxParticipant())
                .setMinParticipant(dto.getMinParticipant())
                .setMajority(dto.isMajority())
                .setDateTime(dto.getDateTime())
                .setCategory(categoryRepository.findByName(dto.getCategory()).orElseThrow(RuntimeException::new));


    }
}
