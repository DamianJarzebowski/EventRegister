package dj.eventregister.category;

import dj.eventregister.event.EventReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    List<String> findAllCategoryNames() {
        return categoryService.findAllCategoryNames();
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryReadDto> findById(@PathVariable long id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<?> saveCategory(@RequestBody CategoryWriteDto categoryWriteDto) {
        CategoryReadDto savedCategory = categoryService.saveCategory(categoryWriteDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCategory.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EventReadDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }



}
