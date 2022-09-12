package dj.eventregister.category.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.dto.CategoryWriteDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryWriteMapper {

    public CategoryWriteDto toDto(Category category) {
        var dto = new CategoryWriteDto();
        dto.setName(category.getName());
        return dto;
    }

    public Category toEntity(CategoryWriteDto dto) {
        var entity = new Category();
        entity.setName(dto.getName());
        return entity;
    }
}
