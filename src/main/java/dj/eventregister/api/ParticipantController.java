package dj.eventregister.api;

import dj.eventregister.models.participant.ParticipantServiceImpl;
import dj.eventregister.models.participant.dto.ParticipantReadDto;
import dj.eventregister.models.participant.dto.ParticipantWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/participants")
class ParticipantController {
    private final ParticipantServiceImpl participantServiceImpl;

    @GetMapping("")
    List<ParticipantReadDto> findAll(String lastName) {
        if (lastName != null)
            return participantServiceImpl.findByLastName(lastName);
        return participantServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantReadDto> findById(@PathVariable Long id) {
        return participantServiceImpl.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<ParticipantWriteDto> saveParticipant(@RequestBody ParticipantWriteDto dto) {
        ParticipantReadDto savedParticipant = participantServiceImpl.saveParticipant(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParticipant.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateParticipant(@PathVariable Long id, @RequestBody ParticipantWriteDto dto) {
        ParticipantReadDto updatedParticipant = participantServiceImpl.updateParticipant(dto, id);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ParticipantReadDto> deleteParticipant(@PathVariable Long id) {
        participantServiceImpl.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
