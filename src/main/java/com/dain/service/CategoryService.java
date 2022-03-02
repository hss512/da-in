package com.dain.service;

import com.dain.domain.entity.Category;
import com.dain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Long getCategoryId(String category){
        Category getCategory = categoryRepository.findCategoryByTitle(category);
        return getCategory.getId();
    }
}
