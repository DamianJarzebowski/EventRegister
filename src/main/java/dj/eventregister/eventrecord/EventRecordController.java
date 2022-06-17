package dj.eventregister.eventrecord;

import dj.eventregister.event.EventDto;
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
@RequestMapping("api/event-record")
class EventRecordController {

    private final EventRecordService eventRecordService;

    @GetMapping("")
    public List<EventRecordDto> findAllEventsRecords() {
        return eventRecordService.findAllEventsRecords();
    }

    @PostMapping("")
    public ResponseEntity<EventRecordDto> registerTheParticipant(@RequestBody EventRecordDto eventRecordDto) {
        EventRecordDto savedParty;
        try {
            savedParty = eventRecordService.registerTheParticipant(eventRecordDto);
        } catch (InvalidPartyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParty.getId())
                .toUri();

        updateEventCurrentParticipants(eventRecordDto);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("")
    public ResponseEntity<Object> updateEventCurrentParticipants(@RequestBody EventRecordDto eventRecordDto) {
        EventDto updatedEvent = eventRecordService.updateEventCurrentParticipants(eventRecordDto);
        return ResponseEntity.ok(updatedEvent);
    }

}
