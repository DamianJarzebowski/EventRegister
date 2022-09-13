package dj.eventregister.event.mapper;

import dj.eventregister.category.CategoryRepository;
import dj.eventregister.event.Event;
import dj.eventregister.event.dto.EventWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventWriteMapper {

    private final CategoryRepository categoryRepository;

    public EventWriteDto toDto(Event event) {
        var dto = new EventWriteDto();

        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setMaxParticipant(event.getMaxParticipant());
        dto.setMinParticipant(event.getMinParticipant());
        dto.setMajority(event.isMajority());
        dto.setDateTime(event.getDateTime());
        dto.setCategory(event.getCategory().getName());
        return dto;
    }

    public Event toEntity(EventWriteDto dto) {
        var entity = new Event();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setMaxParticipant(dto.getMaxParticipant());
        entity.setMinParticipant(dto.getMinParticipant());
        entity.setMajority(dto.isMajority());
        entity.setDateTime(dto.getDateTime());
        var category = categoryRepository.findByName(dto.getCategory()); // W celu zwrócenia z warstwy widoku i przypisania do bazy kategorii wyszukujemy ją po naazwie za pomocą dodatkowej metody z repozytorium
        category.ifPresent(entity::setCategory);
        return entity;
    }
}
