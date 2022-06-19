package dj.eventregister.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryWriteMapper {

    CategoryWriteDto toDto(Category category) {
        var dto = new CategoryWriteDto();
        dto.setName(category.getName());
        return dto;
    }

    Category toEntity(CategoryWriteDto categoryWriteDto) {
        var entity = new Category();
        entity.setName(categoryWriteDto.getName());
        return entity;
    }
}
