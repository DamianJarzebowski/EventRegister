package dj.eventregister.event.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.CategoryRepository;
import dj.eventregister.event.Event;
import dj.eventregister.event.dto.EventWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Event toEntity(EventWriteDto eventWriteDto) {
        var entity = new Event();

        entity.setName(eventWriteDto.getName());
        entity.setDescription(eventWriteDto.getDescription());
        entity.setMaxParticipant(eventWriteDto.getMaxParticipant());
        entity.setMinParticipant(eventWriteDto.getMinParticipant());
        entity.setMajority(eventWriteDto.isMajority());
        entity.setDateTime(eventWriteDto.getDateTime());
        Optional<Category> category = categoryRepository.findByName(eventWriteDto.getCategory()); // W celu zwrócenia z warstwy widoku i przypisania do bazy kategorii wyszukujemy ją po naazwie za pomocą dodatkowej metody z repozytorium
        category.ifPresent(entity::setCategory);
        return entity;
    }
}
