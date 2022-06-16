package dj.eventregister.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    CategoryDto toDto(Category category) {
        var dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    Category toEntity(CategoryDto categoryDto) {
        var entity = new Category();
        entity.setId(categoryDto.getId());
        entity.setName(categoryDto.getName());
        return entity;
    }
}
