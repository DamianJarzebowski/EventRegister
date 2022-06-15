package dj.eventregister.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;


    @GetMapping("")
    public List<EventDto> findAllOrSelectedCategoryOfEvents(@RequestParam(required = false) String category) {
        if (category == null)
            return eventService.findAllEvents();
        return eventService.findAllEventsWithThisCategoryName(category);
    }

    @PostMapping("/save")
    ResponseEntity<EventDto> saveEvent(@RequestBody EventDto eventDto) {
        EventDto savedEvent = eventService.save(eventDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).body(savedEvent);
    }


    // nie da sie skasowac rekordu 1
    @DeleteMapping("/{id}")
    ResponseEntity<EventDto> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
