package dj.eventregister.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    List<String> findAllCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

    public Optional<String> findById(long id) {
        return categoryRepository.findById(id)
                .map(Category::getName);
    }
}
