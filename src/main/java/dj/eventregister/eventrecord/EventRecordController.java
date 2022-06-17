package dj.eventregister.eventrecord;

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
@RequestMapping("api/party")
class EventRecordController {

    private final EventRecordService eventRecordService;

    @GetMapping
    public List<EventRecordDto> findAllEventsRecords() {
        return eventRecordService.findAllEventsRecords();
    }

    @PostMapping("")
    public ResponseEntity<EventRecordDto> registerTheParticipant(@RequestBody EventRecordDto partyDto) {
        EventRecordDto savedParty;
        try {
            savedParty = eventRecordService.registerTheParticipant(partyDto);
        } catch (InvalidPartyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParty.getId())
                .toUri();

        int actualCurrentParticipants = eventRecordService.getEventFromParty(partyDto).getCurrentParticipants();
        eventRecordService.getEventFromParty(partyDto).setCurrentParticipants(actualCurrentParticipants + 1);


        return ResponseEntity.created(location).build();
    }
}
