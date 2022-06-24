package dj.eventregister.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/participants")
class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping("")
    public List<ParticipantReadDto> findAll(String lastName) {
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
    ResponseEntity<ParticipantWriteDto> saveParticipant(@RequestBody ParticipantWriteDto participantWriteDto) {
        ParticipantReadDto savedParticipant = participantService.saveParticipant(participantWriteDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParticipant.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateParticipant(@PathVariable Long id, @RequestBody ParticipantReadDto participantReadDto) {
        if(!id.equals(participantReadDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        ParticipantReadDto updatedParticipant = participantService.updateParticipant(participantReadDto);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ParticipantReadDto> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
