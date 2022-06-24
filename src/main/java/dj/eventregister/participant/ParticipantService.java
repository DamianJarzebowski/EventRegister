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
    private final ParticipantWriteMapper participantWriteMapper;

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

    ParticipantReadDto saveParticipant(ParticipantWriteDto participantWriteDto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(participantWriteDto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(participantWriteDto);
    }

    ParticipantReadDto mapAndSaveParticipant(ParticipantWriteDto participantWriteDto) {
        Participant participantEntity = participantWriteMapper.toEntity(participantWriteDto);
        Participant savedParticipant = participantRepository.save(participantEntity);
        return participantReadMapper.toDto(savedParticipant);
    }

    ParticipantReadDto mapAndUpdateParticipant(ParticipantReadDto participantReadDto) {
        Participant participantEntity = participantReadMapper.toEntity(participantReadDto);
        Participant updatedParticipant = participantRepository.save(participantEntity);
        return participantReadMapper.toDto(updatedParticipant);
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
        return mapAndUpdateParticipant(participantReadDto);
    }

}
