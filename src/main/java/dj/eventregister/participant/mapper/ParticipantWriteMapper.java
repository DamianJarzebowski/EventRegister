package dj.eventregister.participant.mapper;

import dj.eventregister.participant.Participant;
import dj.eventregister.participant.dto.ParticipantWriteDto;
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
