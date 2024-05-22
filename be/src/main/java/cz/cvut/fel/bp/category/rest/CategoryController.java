package cz.cvut.fel.bp.category.rest;

import cz.cvut.fel.bp.api.v1.model.Category;
import cz.cvut.fel.bp.api.v1.rest.CategoriesApi;
import cz.cvut.fel.bp.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author annak
 * @since 2024-03-29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class CategoryController implements CategoriesApi {

    private final CategoryService categoryService;


    @Override
    public ResponseEntity<List<Category>> getCategories() {
        log.debug("Get all categories");
        return ResponseEntity.ok(categoryService.getAll());
    }

}
