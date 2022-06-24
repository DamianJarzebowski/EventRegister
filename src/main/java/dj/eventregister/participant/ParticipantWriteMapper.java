package dj.eventregister.participant;

public class ParticipantWriteMapper {

    private ParticipantWriteMapper() {}

    ParticipantWriteDto toDto(Participant participant) {
        var dto = new ParticipantWriteDto();
        dto.setName(participant.getName());
        dto.setLastName(participant.getLastName());
        dto.setAge(participant.getAge());
        dto.setEmail(participant.getEmail());
        return dto;
    }

    Participant toEntity (ParticipantWriteDto participantWriteDto) {
        var entity = new Participant();
        entity.setName(participantWriteDto.getName());
        entity.setLastName(participantWriteDto.getLastName());
        entity.setAge(participantWriteDto.getAge());
        entity.setEmail(participantWriteDto.getEmail());
        return entity;
    }
}
