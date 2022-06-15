package dj.eventregister.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    List<ParticipantDto> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::toDto)
                .toList();
    }

    ParticipantDto save(ParticipantDto participantDto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(participantDto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(participantDto);
    }

    ParticipantDto mapAndSaveParticipant(ParticipantDto participantDto) {
        Participant participantEntity = ParticipantMapper.toEntity(participantDto);
        Participant savedParticipant = participantRepository.save(participantEntity);
        return ParticipantMapper.toDto(savedParticipant);
    }

    void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }


}
