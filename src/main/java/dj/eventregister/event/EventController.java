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
    // for new Created events change slightly name to avoid name application
    // for updates duplicate name will be rejected
    // validate number of participants 1-100 max > min

    @GetMapping("")
    public List<EventDto> findAllOrSelectedCategoryOfEvents(@RequestParam(required = false) String category) {
        if (category == null)
            return eventService.findAllEvents();
        return eventService.findAllEventsWithThisCategoryName(category);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDto> findById (@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<Object> saveEvent(@RequestBody EventDto eventDto) {
        EventDto savedEvent = eventService.save(eventDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).build();
    }

    // Chyba nie działa coś nie tak z formatem daty ????
    @PutMapping("{id}")
    ResponseEntity<Object> replaceEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        if(!id.equals(eventDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        EventDto updatedEvent = eventService.updateEvent(eventDto);
        return ResponseEntity.ok(updatedEvent);
    }

    // nie da sie skasowac rekordu 1
    @DeleteMapping("/{id}")
    ResponseEntity<EventDto> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
