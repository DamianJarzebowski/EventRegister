package dj.eventregister.event;

import dj.eventregister.category.Category;
import dj.eventregister.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventMapper {

    private final CategoryRepository categoryRepository;

    public EventDto toDto(Event event) {
        var dto = new EventDto();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setMaxParticipant(event.getMaxParticipant());
        dto.setMinParticipant(event.getMinParticipant());
        dto.setMajority(event.isMajority());
        dto.setDateTime(event.getDateTime());
        dto.setCategory(event.getCategory().getName()); // W celu pobrania nazwy kategorii wyciągamy jej nazwe.
        return dto;
    }

    public Event toEntity(EventDto eventDto) {
        var entity = new Event();

        entity.setId(eventDto.getId());
        entity.setName(eventDto.getName());
        entity.setDescription(eventDto.getDescription());
        entity.setMaxParticipant(eventDto.getMaxParticipant());
        entity.setMinParticipant(eventDto.getMinParticipant());
        entity.setMajority(eventDto.isMajority());
        entity.setDateTime(eventDto.getDateTime());
        Optional<Category> category = categoryRepository.findByName(eventDto.getCategory()); // W celu zwrócenia z warstwy widoku i przypisania do bazy kategorii wyszukujemy ją po naazwie za pomocą dodatkowej metody z repozytorium
        category.ifPresent(entity::setCategory);
        return entity;
    }
}
