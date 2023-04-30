package dj.eventregister.models.category.mapper;

import dj.eventregister.models.category.Category;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;

@UtilityClass
public class CategoryWriteMapper {

    public static Category toEntity(CategoryWriteDto dto) {
        return new Category()
                .setName(dto.getName());
    }
}
