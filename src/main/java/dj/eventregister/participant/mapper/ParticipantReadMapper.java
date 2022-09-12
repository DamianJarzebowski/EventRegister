package dj.eventregister.participant.mapper;

import dj.eventregister.participant.Participant;
import dj.eventregister.participant.dto.ParticipantReadDto;
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

     public Participant toEntity (ParticipantReadDto participantReadDto) {
        var entity = new Participant();
        entity.setId(participantReadDto.getId());
        entity.setName(participantReadDto.getName());
        entity.setLastName(participantReadDto.getLastName());
        entity.setAge(participantReadDto.getAge());
        entity.setEmail(participantReadDto.getEmail());
        return entity;
    }
}
