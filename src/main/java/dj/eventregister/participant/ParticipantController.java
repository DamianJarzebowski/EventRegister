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
    public List<ParticipantDto> findAll(String lastName) {
        if (lastName != null)
            return participantService.findByLastName(lastName);
        return participantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> findById(@PathVariable Long id) {
        return participantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<ParticipantDto> saveParticipant(@RequestBody ParticipantDto participantDto) {
        ParticipantDto savedParticipant = participantService.save(participantDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParticipant.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> replaceParticipant(@PathVariable Long id, @RequestBody ParticipantDto participantDto) {
        if(!id.equals(participantDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        ParticipantDto updatedParticipant = participantService.updateParticipant(participantDto);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ParticipantDto> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
