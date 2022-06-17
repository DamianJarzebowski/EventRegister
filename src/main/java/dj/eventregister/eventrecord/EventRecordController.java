package dj.eventregister.eventrecord;

import dj.eventregister.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/party")
class EventRecordController {

    private final EventRecordService partyService;
    private final EventService eventService;

    @PostMapping("")
    public ResponseEntity<EventRecordDto> registerTheParticipant(@RequestBody EventRecordDto partyDto) {
        EventRecordDto savedParty;
        try {
            savedParty = partyService.registerTheParticipant(partyDto);
        } catch (InvalidPartyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParty.getId())
                .toUri();

        int actualCurrentParticipants = partyService.getEventFromParty(partyDto).getCurrentParticipants();
        partyService.getEventFromParty(partyDto).setCurrentParticipants(actualCurrentParticipants + 1);


        return ResponseEntity.created(location).build();
    }
}
