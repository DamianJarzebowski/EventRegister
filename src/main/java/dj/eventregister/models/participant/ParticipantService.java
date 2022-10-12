package dj.eventregister.models.participant;

import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import dj.eventregister.models.participant.mapper.ParticipantReadMapper;
import dj.eventregister.models.participant.mapper.ParticipantWriteMapper;
import dj.eventregister.repository.ParticipantRepository;
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

    public List<ParticipantReadDto> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(participantReadMapper::toDto)
                .toList();
    }

    public List<ParticipantReadDto> findByLastName(String lastName) {
        return participantRepository.findAllByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(participantReadMapper::toDto)
                .toList();
    }

    public Optional<ParticipantReadDto> findById(long id) {
        return  participantRepository.findById(id).map(participantReadMapper::toDto);
    }

    public ParticipantReadDto saveParticipant(ParticipantWriteDto dto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(dto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
        return mapAndSaveParticipant(dto);
    }

    public ParticipantReadDto updateParticipant(ParticipantWriteDto dto, Long id) {
        Optional<Participant> participantById = participantRepository.findByEmail(dto.getEmail());
        participantById.ifPresent(participant -> {
            if (!participant.getId().equals(id))
                throw new DuplicateEmailException();
        });
        return mapAndUpdateParticipant(dto, id);
    }

    private ParticipantReadDto mapAndSaveParticipant(ParticipantWriteDto dto) {
        Participant participantEntity = participantWriteMapper.toEntity(dto);
        return saveAndMap(participantEntity);
    }

    private ParticipantReadDto mapAndUpdateParticipant(ParticipantWriteDto dto, Long id) {
        Participant participantEntity = participantReadMapper.toEntity(dto, id);
        return saveAndMap(participantEntity);
    }

    private ParticipantReadDto saveAndMap(Participant participant) {
        Participant savedParticipant = participantRepository.save(participant);
        return participantReadMapper.toDto(savedParticipant);
    }

    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

}
