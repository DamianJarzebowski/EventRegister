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

    public Optional<String> findById(long id) {
        return categoryRepository.findById(id)
                .map(Category::getName);
    }

    Long findId(CategoryWriteDto categoryWriteDto) {
        return categoryWriteMapper.toEntity(categoryWriteDto).getId();
    }

    CategoryWriteDto saveCategory(CategoryWriteDto categoryWriteDto) {
        Optional<Category> eventByName = categoryRepository.findByName(categoryWriteDto.getName());
        eventByName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return mapAndSaveCategory(categoryWriteDto);
    }

    CategoryWriteDto mapAndSaveCategory (CategoryWriteDto categoryWriteDto) {
        Category category = categoryWriteMapper.toEntity(categoryWriteDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryWriteMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
