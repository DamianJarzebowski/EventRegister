package dj.eventregister.participant.mapper;

import dj.eventregister.participant.Participant;
import dj.eventregister.participant.dto.ParticipantReadDto;
import dj.eventregister.participant.dto.ParticipantWriteDto;
import org.springframework.stereotype.Service;

@Service
public class ParticipantReadMapper {

    public ParticipantReadDto toDto(Participant participant) {
        var dto = new ParticipantReadDto();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setLastName(participant.getLastName());
        dto.setAge(participant.getAge());
        dto.setEmail(participant.getEmail());
        return dto;
    }

     public Participant toEntity (ParticipantWriteDto dto, Long id) {
        var entity = new Participant();
        entity.setId(id);
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
