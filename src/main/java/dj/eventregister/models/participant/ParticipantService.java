package dj.eventregister.models.participant;

import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {

    List<ParticipantReadDto> findAll();

    List<ParticipantReadDto> findByLastName(String lastName);

    Optional<ParticipantReadDto> findById(long id);

    ParticipantReadDto saveParticipant(ParticipantWriteDto dto);

    ParticipantReadDto updateParticipant(ParticipantWriteDto dto, long id);

    void deleteParticipant(Long id);
}
