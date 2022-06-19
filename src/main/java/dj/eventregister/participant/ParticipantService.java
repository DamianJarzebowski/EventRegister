package dj.eventregister.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    List<ParticipantDto> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(participantMapper::toDto)
                .toList();
    }

    List<ParticipantDto> findByLastName(String lastName) {
        return participantRepository.findAllByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(participantMapper::toDto)
                .toList();
    }

    Optional<ParticipantDto> findById(long id) {
        return  participantRepository.findById(id).map(participantMapper::toDto);
    }

    ParticipantDto save(ParticipantDto participantDto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(participantDto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(participantDto);
    }

    ParticipantDto mapAndSaveParticipant(ParticipantDto participantDto) {
        Participant participantEntity = participantMapper.toEntity(participantDto);
        Participant savedParticipant = participantRepository.save(participantEntity);
        return participantMapper.toDto(savedParticipant);
    }

    void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }


    ParticipantDto updateParticipant(ParticipantDto participantDto) {
        Optional<Participant> participantById = participantRepository.findByEmail(participantDto.getEmail());
        participantById.ifPresent(participant -> {
            if (!participant.getId().equals(participantDto.getId()))
                throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(participantDto);
    }

}
