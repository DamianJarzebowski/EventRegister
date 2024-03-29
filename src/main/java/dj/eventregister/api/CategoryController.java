package dj.eventregister.api;

import dj.eventregister.models.category.Category;
import dj.eventregister.models.category.CategoryService;
import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import dj.eventregister.models.event.dto.EventReadDto;
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
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    ResponseEntity<Category> saveCategory(@RequestBody CategoryWriteDto dto) {
        CategoryReadDto savedCategory = categoryService.saveCategory(dto);
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
