package dj.eventregister.models.participant.mapper;

import dj.eventregister.models.participant.Participant;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParticipantWriteMapper {

    public static Participant toEntity (ParticipantWriteDto dto) {
        return new Participant()
                .setName(dto.getName())
                .setLastName(dto.getLastName())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail());
    }
}
