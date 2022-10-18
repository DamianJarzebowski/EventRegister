package dj.eventregister.models.category;

import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import dj.eventregister.models.category.mapper.CategoryReadMapper;
import dj.eventregister.models.category.mapper.CategoryWriteMapper;
import dj.eventregister.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryReadMapper categoryReadMapper;
    private final CategoryWriteMapper categoryWriteMapper;

    public List<String> findAllCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

    public Optional<CategoryReadDto> findById(long id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::toDto);
    }

    public CategoryReadDto saveCategory(CategoryWriteDto dto) {
        Optional<Category> categoryName = categoryRepository.findByName(dto.getName());
        categoryName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return mapAndSaveCategory(dto);
    }

    private CategoryReadDto mapAndSaveCategory (CategoryWriteDto dto) {
        Category category = categoryWriteMapper.toEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        return categoryReadMapper.toDto(savedCategory);
    }

    public  CategoryReadDto newSave(CategoryWriteDto dto) {
        Optional<Category> categoryName = categoryRepository.findByName(dto.getName());
        categoryName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return categoryReadMapper.toDto(categoryRepository.save(categoryWriteMapper.toEntity(dto)));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
