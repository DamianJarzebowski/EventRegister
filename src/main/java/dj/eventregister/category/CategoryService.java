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

    CategoryWriteDto saveCategory(CategoryWriteDto categoryWriteDto) {
        Optional<Category> eventByName = categoryRepository.findByName(categoryWriteDto.getName());
        eventByName.ifPresent(a -> {
            throw new DuplicateCategoryNameException();
        });
        return mapAndSaveCategory(categoryWriteDto);
    }

    /*
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

     */

    CategoryWriteDto mapAndSaveCategory (CategoryWriteDto categoryWriteDto) {
        Category category = categoryWriteMapper.toEntity(categoryWriteDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryWriteMapper.toDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
