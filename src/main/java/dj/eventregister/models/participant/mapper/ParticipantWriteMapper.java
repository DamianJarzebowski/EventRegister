package dj.eventregister.models.participant.mapper;

import dj.eventregister.models.participant.Participant;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import org.springframework.stereotype.Service;

@Service
public class ParticipantWriteMapper {

    public Participant toEntity (ParticipantWriteDto dto) {
        return new Participant()
                .setName(dto.getName())
                .setLastName(dto.getLastName())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail());
    }
}
