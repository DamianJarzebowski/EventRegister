package dj.eventregister.models.category.mapper;

import dj.eventregister.models.category.Category;
import dj.eventregister.models.category.dto.CategoryWriteDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryWriteMapper {

    public Category toEntity(CategoryWriteDto dto) {
        return new Category()
                .setName(dto.getName());
    }
}
