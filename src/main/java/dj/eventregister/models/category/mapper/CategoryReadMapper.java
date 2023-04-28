package dj.eventregister.models.category.mapper;

import dj.eventregister.models.category.Category;
import dj.eventregister.models.category.dto.CategoryReadDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryReadMapper {

    public static CategoryReadDto toDto(Category category) {
        return new CategoryReadDto()
                .setId(category.getId())
                .setName(category.getName());
    }
}
