package dj.eventregister.category.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.dto.CategoryReadDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryReadMapper {

    public CategoryReadDto toDto(Category category) {
        var dto = new CategoryReadDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public Category toEntity(CategoryReadDto dto) {
        var entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
