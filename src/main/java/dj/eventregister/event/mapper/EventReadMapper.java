package dj.eventregister.event.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.CategoryRepository;
import dj.eventregister.event.Event;
import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.event.dto.EventWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventReadMapper {

    private final CategoryRepository categoryRepository;

    public EventReadDto toDto(Event event) {
        var dto = new EventReadDto();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setMaxParticipant(event.getMaxParticipant());
        dto.setMinParticipant(event.getMinParticipant());
        dto.setMajority(event.isMajority());
        dto.setDateTime(event.getDateTime());
        dto.setCategory(event.getCategory().getName());
        dto.setStateEvent(event.getStateEvent());
        return dto;
    }

    public Event toEntity(EventWriteDto dto, Long id) {
        var entity = new Event();

        entity.setId(id);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setMaxParticipant(dto.getMaxParticipant());
        entity.setMinParticipant(dto.getMinParticipant());
        entity.setMajority(dto.isMajority());
        entity.setDateTime(dto.getDateTime());
        Optional<Category> category = categoryRepository.findByName(dto.getCategory()); // W celu zwrócenia z warstwy widoku i przypisania do bazy kategorii wyszukujemy ją po naazwie za pomocą dodatkowej metody z repozytorium
        category.ifPresent(entity::setCategory);
        return entity;
    }
}
