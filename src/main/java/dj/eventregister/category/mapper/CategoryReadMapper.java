package dj.eventregister.category.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.dto.CategoryReadDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryReadMapper {

    public CategoryReadDto toDto(Category category) {
        return new CategoryReadDto()
                .setId(category.getId())
                .setName(category.getName());
    }

    public Category toEntity(CategoryReadDto dto) {
        return new Category()
                .setId(dto.getId())
                .setName(dto.getName());
    }
}
