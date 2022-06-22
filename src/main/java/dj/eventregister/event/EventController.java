package dj.eventregister.event;

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
@RequestMapping("/api/event")
class EventController {

    private final EventService eventService;

    // How to make Slavic Happy
    // for new Created events change slightly name to avoid name duplication
    // for updates duplicate name will be rejected
    // validate number of participants 1-100 max > min

    @GetMapping("")
    public List<EventReadDto> findAllOrSelectedCategoryOfEvents(@RequestParam(required = false) String category) {
        if (category == null)
            return eventService.findAllEvents();
        return eventService.findAllEventsWithThisCategoryName(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventWriteDto> findById (@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<Object> saveEvent(@RequestBody EventReadDto eventReadDto) {
        EventReadDto savedEvent = eventService.save(eventReadDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> replaceEvent(@PathVariable Long id, @RequestBody EventReadDto eventReadDto) {
        if(!id.equals(eventReadDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        EventReadDto updatedEvent = eventService.updateEvent(eventReadDto);
        return ResponseEntity.ok(updatedEvent);
    }

    // nie da sie skasowac rekordu 1
    @DeleteMapping("/{id}")
    ResponseEntity<EventReadDto> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
