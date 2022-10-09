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

    public Event toEntity(EventWriteDto dto) {
        return new Event()
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setMaxParticipant(dto.getMaxParticipant())
                .setMinParticipant(dto.getMinParticipant())
                .setMajority(dto.isMajority())
                .setDateTime(dto.getDateTime())
                .setCategory(categoryRepository.findByName(dto.getCategory()).orElseThrow(RuntimeException::new));
    }
}
