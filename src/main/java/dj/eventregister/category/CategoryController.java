package dj.eventregister.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
