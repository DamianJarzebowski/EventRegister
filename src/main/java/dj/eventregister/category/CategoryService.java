package dj.eventregister.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryReadMapper categoryReadMapper;
    private final CategoryWriteMapper categoryWriteMapper;

    List<String> findAllCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

     Optional<String> findById(long id) {
        return categoryRepository.findById(id)
                .map(Category::getName);
    }

    CategoryReadDto saveCategory(CategoryWriteDto categoryWriteDto) {
        Optional<Category> eventByName = categoryRepository.findByName(categoryWriteDto.getName());
        eventByName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return mapAndSaveCategory(categoryWriteDto);
    }

    CategoryReadDto mapAndSaveCategory (CategoryWriteDto categoryWriteDto) {
        Category category = categoryWriteMapper.toEntity(categoryWriteDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryReadMapper.toDto(savedCategory);
    }

    void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
