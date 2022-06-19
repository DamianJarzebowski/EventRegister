package dj.eventregister.category;

import dj.eventregister.event.EventReadDto;
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
    private final CategoryRepository categoryRepository;

    @GetMapping("")
    public List<String> findAllCategoryNames() {
        return categoryService.findAllCategoryNames();
    }

    @GetMapping("/{id}")
    Optional<String> findById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping("")
    ResponseEntity<Object> saveCategory(@RequestBody CategoryWriteDto categoryWriteDto) {
        categoryService.saveCategory(categoryWriteDto);
        Optional<Category> categoryReadDto = categoryRepository.findByName(categoryWriteDto.getName());
        if (categoryReadDto.isPresent()) {
            URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(categoryReadDto.get().getId())
                    .toUri();
        return ResponseEntity.created(savedCompanyUri).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EventReadDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
