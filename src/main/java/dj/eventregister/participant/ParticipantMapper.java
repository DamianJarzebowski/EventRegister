package dj.eventregister.participant;

import org.springframework.stereotype.Service;

@Service
class ParticipantMapper {

    private ParticipantMapper() {}

    static ParticipantDto toDto (Participant participant) {
        var dto = new ParticipantDto();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setLastName(participant.getLastName());
        dto.setAge(participant.getAge());
        dto.setEmail(participant.getEmail());
        return dto;
    }

    static Participant toEntity (ParticipantDto participantDto) {
        var entity = new Participant();
        entity.setId(participantDto.getId());
        entity.setName(participantDto.getName());
        entity.setLastName(participantDto.getLastName());
        entity.setAge(participantDto.getAge());
        entity.setEmail(participantDto.getEmail());
        return entity;
    }
}