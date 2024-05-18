package cz.cvut.fel.bp.category.mapper;

import cz.cvut.fel.bp.api.v1.model.Category;
import cz.cvut.fel.bp.category.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryEntity2CategoryMapper {

    public Category toCategory(CategoryEntity categoryEntity) {

        if (categoryEntity == null) return null;

        Category category = new Category();
        category.setName(categoryEntity.getName());

        return category;
    }


    public List<Category> toCategoryList(List<CategoryEntity> categoryEntities) {
        if (categoryEntities == null) return null;

        List<Category> categories = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryEntities) {
            Category category = toCategory(categoryEntity);
            categories.add(category);
        }

        return categories;
    }

}
