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
@RequestMapping("api/event-record")
class EventRecordController {

    private final EventRecordService eventRecordService;

    @GetMapping("")
    List<EventRecordReadDto> findAllEventsRecords() {
        return eventRecordService.findAllEventsRecords();
    }

    @GetMapping("/{id}")
    ResponseEntity<EventRecordReadDto> findById(@PathVariable long id) {
        return eventRecordService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<?> registerTheParticipant(@RequestBody EventRecordWriteDto eventRecordWriteDto) {
        EventRecordReadDto savedParty;
        try {
            savedParty = eventRecordService.registerTheParticipant(eventRecordWriteDto);
        } catch (InvalidEventRecordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedParty.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteEventRecord(@PathVariable Long id) {
        eventRecordService.deleteEventRecord(id);
        return ResponseEntity.noContent().build();
    }

}
