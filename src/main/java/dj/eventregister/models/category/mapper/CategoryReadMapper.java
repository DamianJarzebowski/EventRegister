package dj.eventregister.models.category.mapper;

import dj.eventregister.models.category.Category;
import dj.eventregister.models.category.dto.CategoryReadDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryReadMapper {

    public CategoryReadDto toDto(Category category) {
        return new CategoryReadDto()
                .setId(category.getId())
                .setName(category.getName());
    }
}
