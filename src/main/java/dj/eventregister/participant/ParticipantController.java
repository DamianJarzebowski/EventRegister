package dj.eventregister.participant;

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
    public List<ParticipantDto> findAll() {
        return participantService.findAll();
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

    @DeleteMapping("{id}")
    ResponseEntity<ParticipantDto> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
