package dj.eventregister.participant;

import dj.eventregister.participant.dto.ParticipantReadDto;
import dj.eventregister.participant.dto.ParticipantWriteDto;
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

    private final ParticipantService participantService;

    @GetMapping("")
    List<ParticipantReadDto> findAll(String lastName) {
        if (lastName != null)
            return participantService.findByLastName(lastName);
        return participantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantReadDto> findById(@PathVariable Long id) {
        return participantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<ParticipantWriteDto> saveParticipant(@RequestBody ParticipantWriteDto dto) {
        ParticipantReadDto savedParticipant = participantService.saveParticipant(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParticipant.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateParticipant(@PathVariable Long id, @RequestBody ParticipantWriteDto dto) {
        ParticipantReadDto updatedParticipant = participantService.updateParticipant(dto, id);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ParticipantReadDto> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
