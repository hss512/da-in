package com.dain.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryDTO {

    @Getter
    @NoArgsConstructor
    public static class BoardCategoryDTO{
        private String categoryName;

        @Builder
        public BoardCategoryDTO(String categoryName){
            this.categoryName = categoryName;
        }
    }
}
