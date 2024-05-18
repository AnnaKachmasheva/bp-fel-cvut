package cz.cvut.fel.bp.category.service.impl;

import cz.cvut.fel.bp.api.v1.model.Category;
import cz.cvut.fel.bp.category.entity.CategoryEntity;
import cz.cvut.fel.bp.category.mapper.CategoryEntity2CategoryMapper;
import cz.cvut.fel.bp.category.repository.CategoryEntityRepository;
import cz.cvut.fel.bp.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author annak
 * @since 2024-05-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryEntityRepository categoryEntityRepository;

    private final CategoryEntity2CategoryMapper categoryEntity2CategoryMapper;


    @Override
    public List<Category> getAll() {
        log.debug("Find all categories");

        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();

        log.debug("Find {} categories.", categoryEntities.size());

        return categoryEntity2CategoryMapper.toCategoryList(categoryEntities);
    }

}
