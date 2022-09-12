package dj.eventregister.participant.mapper;

import dj.eventregister.participant.Participant;
import dj.eventregister.participant.dto.ParticipantWriteDto;
import org.springframework.stereotype.Service;

@Service
public class ParticipantWriteMapper {

    public ParticipantWriteDto toDto(Participant participant) {
        var dto = new ParticipantWriteDto();
        dto.setName(participant.getName());
        dto.setLastName(participant.getLastName());
        dto.setAge(participant.getAge());
        dto.setEmail(participant.getEmail());
        return dto;
    }

    public Participant toEntity (ParticipantWriteDto dto) {
        var entity = new Participant();
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
