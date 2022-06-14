package dj.eventregister.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    List<String> findAllCategoryNames() {
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .toList();
    }
}
