package cz.cvut.fel.bp.category.service;

import cz.cvut.fel.bp.api.v1.model.Category;

import java.util.List;

/**
 * @author annak
 * @since 2024-05-09
 */
public interface CategoryService {

    /**
     *
     * @return A list of categories.
     */
    List<Category> getAll();

}
