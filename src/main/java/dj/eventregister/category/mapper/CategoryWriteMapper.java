package dj.eventregister.category.mapper;

import dj.eventregister.category.Category;
import dj.eventregister.category.dto.CategoryWriteDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryWriteMapper {

    public Category toEntity(CategoryWriteDto dto) {
        return new Category()
                .setName(dto.getName());
    }
}
