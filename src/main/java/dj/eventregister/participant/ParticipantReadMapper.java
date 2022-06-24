package dj.eventregister.participant;

import org.springframework.stereotype.Service;

@Service
class ParticipantReadMapper {

    ParticipantReadDto toDto(Participant participant) {
        var dto = new ParticipantReadDto();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setLastName(participant.getLastName());
        dto.setAge(participant.getAge());
        dto.setEmail(participant.getEmail());
        return dto;
    }

     Participant toEntity (ParticipantReadDto participantReadDto) {
        var entity = new Participant();
        entity.setId(participantReadDto.getId());
        entity.setName(participantReadDto.getName());
        entity.setLastName(participantReadDto.getLastName());
        entity.setAge(participantReadDto.getAge());
        entity.setEmail(participantReadDto.getEmail());
        return entity;
    }
}
