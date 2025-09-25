package com.jperez.timerapp.feature.mapper

import com.jperez.timerapp.domain.model.Category
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.CategoryUI

class CategoryUIMapper {

    fun mapCategoryToCategoryUI(category: Category): CategoryUI {
        return CategoryUI(
            name = category.name,
            color = CategoryColor.valueOf(category.color),
            categoryType = CategoryType.valueOf(category.type),
        )
    }
}