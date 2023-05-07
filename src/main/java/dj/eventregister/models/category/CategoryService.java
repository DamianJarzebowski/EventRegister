package dj.eventregister.models.category;

import dj.eventregister.models.category.dto.CategoryReadDto;
import dj.eventregister.models.category.dto.CategoryWriteDto;

import java.util.List;

public interface CategoryService {

    List<String> findAllCategoryNames();

    CategoryReadDto findById(long id);

    CategoryReadDto saveCategory(CategoryWriteDto dto);

    void deleteCategory(long id);



}
