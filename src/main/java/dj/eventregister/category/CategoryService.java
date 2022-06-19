package dj.eventregister.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

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

    CategoryDto save(CategoryDto categoryDto) {
        Optional<Category> eventByName = categoryRepository.findByName(categoryDto.getName());
        eventByName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return mapAndSaveCategory(categoryDto);
    }

    CategoryDto mapAndSaveCategory (CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
