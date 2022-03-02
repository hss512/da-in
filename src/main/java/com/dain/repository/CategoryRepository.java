package com.dain.repository;

import com.dain.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByTitle(String categoryName);
    Category findCategoryById(Long categoryId);
}
