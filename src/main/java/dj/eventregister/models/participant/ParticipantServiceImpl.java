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
public class ParticipantServiceImpl implements ParticipantService {

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
        checkEmailPresentAndThrowExceptionIfExist(dto);
        return participantReadMapper.toDto(
                participantRepository.save(participantWriteMapper.toEntity(dto)));
    }

    public ParticipantReadDto updateParticipant(ParticipantWriteDto dto, long id) {
        checkEmailPresentAndThrowExceptionIfExist(dto);
        return participantReadMapper.toDto(
                participantRepository.save(participantReadMapper.toEntity(dto, id)));
    }

    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    private void checkEmailPresentAndThrowExceptionIfExist(ParticipantWriteDto dto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(dto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
    }
}
