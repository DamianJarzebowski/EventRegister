package dj.eventregister.models.participant;

import dj.eventregister.exceptions.ErrorMessage;
import dj.eventregister.exceptions.notFound.NotFoundException;
import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import dj.eventregister.models.participant.mapper.ParticipantReadMapper;
import dj.eventregister.models.participant.mapper.ParticipantWriteMapper;
import dj.eventregister.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    public List<ParticipantReadDto> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantReadMapper::toDto)
                .toList();
    }

    public List<ParticipantReadDto> findByLastName(String lastName) {
        return participantRepository.findAllByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(ParticipantReadMapper::toDto)
                .toList();
    }

    public Optional<ParticipantReadDto> findById(long id) {
        return  participantRepository.findById(id).map(ParticipantReadMapper::toDto);
    }
    
    public ParticipantReadDto saveParticipant(ParticipantWriteDto dto) {
        checkEmailPresentAndThrowExceptionIfExist(dto);
        return ParticipantReadMapper.toDto(
                participantRepository.save(ParticipantWriteMapper.toEntity(dto)));
    }

    @Transactional
    public ParticipantReadDto updateParticipant(ParticipantWriteDto dto, long id) {
        checkEmailPresentAndThrowExceptionIfExist(dto);
        var actual = participantRepository.findById(id).orElseThrow();
        actual.setName(dto.getName())
                .setLastName(dto.getLastName())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail());
        return ParticipantReadMapper.toDto(actual);
    }

    public void deleteParticipant(Long id) {
        var actual = participantRepository.findById(id).orElseThrow(() -> {
            log.error("Model id: {} does not exists", id);
            return new NotFoundException(ErrorMessage.NOT_FOUND);
        });
        participantRepository.deleteById(actual.getId());
    }

    private void checkEmailPresentAndThrowExceptionIfExist(ParticipantWriteDto dto) {
        Optional<Participant> participantByEmail = participantRepository.findByEmail(dto.getEmail());
        participantByEmail.ifPresent(participant -> {
            throw new DuplicateEmailException();
        });
    }
}
