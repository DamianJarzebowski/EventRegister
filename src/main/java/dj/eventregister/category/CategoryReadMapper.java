package dj.eventregister.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryReadMapper {

    CategoryReadDto toDto(Category category) {
        var dto = new CategoryReadDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    Category toEntity(CategoryReadDto categoryReadDto) {
        var entity = new Category();
        entity.setId(categoryReadDto.getId());
        entity.setName(categoryReadDto.getName());
        return entity;
    }
}
