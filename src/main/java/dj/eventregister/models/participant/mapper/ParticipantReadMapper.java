package dj.eventregister.models.participant.mapper;

import dj.eventregister.models.participant.Participant;
import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParticipantReadMapper {

    public static ParticipantReadDto toDto(Participant participant) {
        return new ParticipantReadDto()
                .setId(participant.getId())
                .setName(participant.getName())
                .setLastName(participant.getLastName())
                .setAge(participant.getAge())
                .setEmail(participant.getEmail());
    }

     public static Participant toEntity (ParticipantWriteDto dto, Long id) {
        return new Participant()
                .setId(id)
                .setName(dto.getName())
                .setLastName(dto.getLastName())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail());
    }
}
