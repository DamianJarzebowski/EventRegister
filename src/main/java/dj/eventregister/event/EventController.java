package dj.eventregister.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;


    @GetMapping("")
    public List<EventDto> findAllOrSelectedCategoryOfEvents(@RequestParam(required = false) String category) {
        if (category.isEmpty())
            return eventService.findAllEvents();
        return eventService.findAllEventsWithThisCategoryName(category);
    }

    @PostMapping("/save")
    ResponseEntity<EventDto> saveEvent() {
        return null;
    }


}
