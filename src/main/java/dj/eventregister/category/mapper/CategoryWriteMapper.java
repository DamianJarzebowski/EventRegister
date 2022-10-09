package dj.eventregister.category.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.dto.CategoryWriteDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryWriteMapper {

    public CategoryWriteDto toDto(Category category) {
        return new CategoryWriteDto()
                .setName(category.getName());
    }

    public Category toEntity(CategoryWriteDto dto) {
        return new Category()
                .setName(dto.getName());
    }
}
