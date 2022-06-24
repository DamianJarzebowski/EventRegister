package dj.eventregister.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryWriteMapper {

    private final CategoryRepository categoryRepository;

    CategoryWriteDto toDto(Category category) {
        var dto = new CategoryWriteDto();
        dto.setName(category.getName());
        return dto;
    }

    Category toEntity(CategoryWriteDto categoryWriteDto) {
        var entity = new Category();
        entity.setName(categoryWriteDto.getName());
        return entity;
    }
}
