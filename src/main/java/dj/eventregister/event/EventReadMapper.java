package dj.eventregister.event;

import dj.eventregister.category.Category;
import dj.eventregister.category.CategoryRepository;
import dj.eventregister.eventrecord.EventRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventReadMapper {

    private final CategoryRepository categoryRepository;
    private final EventRecordRepository eventRecordRepository;

    public EventReadDto toDto(Event event) {
        var dto = new EventReadDto();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setMaxParticipant(event.getMaxParticipant());
        dto.setMinParticipant(event.getMinParticipant());
        dto.setCurrentParticipants(sumParticipants(event));
        dto.setMajority(event.isMajority());
        dto.setDateTime(event.getDateTime());
        dto.setCategory(event.getCategory().getName());
        return dto;
    }

    public int sumParticipants(Event event) {
        return eventRecordRepository.findAll()
                .stream()
                .filter(eventRecord -> eventRecord.getEvent().equals(event))
                .toList()
                .size();
    }

    public Event toEntity(EventReadDto eventReadDto) {
        var entity = new Event();

        entity.setId(eventReadDto.getId());
        entity.setName(eventReadDto.getName());
        entity.setDescription(eventReadDto.getDescription());
        entity.setMaxParticipant(eventReadDto.getMaxParticipant());
        entity.setMinParticipant(eventReadDto.getMinParticipant());
        entity.setCurrentParticipants(eventReadDto.getCurrentParticipants());
        entity.setMajority(eventReadDto.isMajority());
        entity.setDateTime(eventReadDto.getDateTime());
        Optional<Category> category = categoryRepository.findByName(eventReadDto.getCategory()); // W celu zwrócenia z warstwy widoku i przypisania do bazy kategorii wyszukujemy ją po naazwie za pomocą dodatkowej metody z repozytorium
        category.ifPresent(entity::setCategory);
        return entity;
    }
}
