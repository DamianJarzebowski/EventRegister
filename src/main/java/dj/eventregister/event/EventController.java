package dj.eventregister.event;

import dj.eventregister.event.dto.EventReadDto;
import dj.eventregister.event.dto.EventWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/event")
class EventController {

    private final EventService eventService;

    @GetMapping("")
    List<EventReadDto> findAllOrSelectedCategoryOfEvents(@RequestParam(required = false) String category) {
        if (category == null)
            return eventService.findAllEvents();
        return eventService.findAllEventsWithThisCategoryName(category);
    }

    @GetMapping("/{id}")
    ResponseEntity<EventReadDto> findById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    ResponseEntity<Object> saveEvent(@RequestBody EventWriteDto dto) {
        EventReadDto savedEvent;
        try {
            savedEvent = eventService.save(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateEvent(@PathVariable Long id, @RequestBody EventWriteDto dto) {
        var updatedEvent = eventService.update(dto, id);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EventReadDto> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
