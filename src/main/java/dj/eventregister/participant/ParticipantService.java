package dj.eventregister.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantReadMapper participantReadMapper;

    List<ParticipantReadDto> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(participantReadMapper::toDto)
                .toList();
    }

    List<ParticipantReadDto> findByLastName(String lastName) {
        return participantRepository.findAllByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(participantReadMapper::toDto)
                .toList();
    }

    Optional<ParticipantReadDto> findById(long id) {
        return  participantRepository.findById(id).map(participantReadMapper::toDto);
    }

    ParticipantReadDto save(ParticipantReadDto participantReadDto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(participantReadDto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(participantReadDto);
    }

    ParticipantReadDto mapAndSaveParticipant(ParticipantReadDto participantReadDto) {
        Participant participantEntity = participantReadMapper.toEntity(participantReadDto);
        Participant savedParticipant = participantRepository.save(participantEntity);
        return participantReadMapper.toDto(savedParticipant);
    }

    void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }


    ParticipantReadDto updateParticipant(ParticipantReadDto participantReadDto) {
        Optional<Participant> participantById = participantRepository.findByEmail(participantReadDto.getEmail());
        participantById.ifPresent(participant -> {
            if (!participant.getId().equals(participantReadDto.getId()))
                throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(participantReadDto);
    }

}
