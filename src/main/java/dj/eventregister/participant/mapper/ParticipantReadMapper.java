package dj.eventregister.participant.mapper;

import dj.eventregister.participant.Participant;
import dj.eventregister.participant.dto.ParticipantReadDto;
import dj.eventregister.participant.dto.ParticipantWriteDto;
import org.springframework.stereotype.Service;

@Service
public class ParticipantReadMapper {

    public ParticipantReadDto toDto(Participant participant) {
        return new ParticipantReadDto()
                .setId(participant.getId())
                .setName(participant.getName())
                .setLastName(participant.getLastName())
                .setAge(participant.getAge())
                .setEmail(participant.getEmail());
    }

     public Participant toEntity (ParticipantWriteDto dto, Long id) {
        return new Participant()
                .setId(id)
                .setName(dto.getName())
                .setLastName(dto.getLastName())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail());
    }
}
