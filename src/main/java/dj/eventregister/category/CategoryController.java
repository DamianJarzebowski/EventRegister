package dj.eventregister.category;

import dj.eventregister.event.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public List<String> findAllCategoryNames() {
        return categoryService.findAllCategoryNames();
    }

    @GetMapping("/{id}")
    Optional<String> findById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping("")
    ResponseEntity<Object> saveCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedEvent = categoryService.save(categoryDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).build();
    }

}
