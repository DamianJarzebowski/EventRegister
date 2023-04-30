package dj.eventregister.models.category;

import dj.eventregister.exceptions.ErrorMessage;
import dj.eventregister.exceptions.notFound.NotFoundException;
import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import dj.eventregister.models.category.mapper.CategoryReadMapper;
import dj.eventregister.models.category.mapper.CategoryWriteMapper;
import dj.eventregister.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public List<String> findAllCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

    public Optional<CategoryReadDto> findById(long id) {
        return categoryRepository.findById(id)
                .map(CategoryReadMapper::toDto);
    }

    public  CategoryReadDto saveCategory(CategoryWriteDto dto) {
        Optional<Category> categoryName = categoryRepository.findByName(dto.getName());
        categoryName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return CategoryReadMapper.toDto(categoryRepository.save(CategoryWriteMapper.toEntity(dto)));
    }

    public void deleteCategory(long id) {
        findById(id).orElseThrow(() -> {
            log.error("Category id: {} does not exists", id);
            return new NotFoundException(ErrorMessage.NOT_FOUND);
        });
        categoryRepository.deleteById(id);
    }
}
