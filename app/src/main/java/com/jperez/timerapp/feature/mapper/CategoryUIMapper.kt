package com.jperez.timerapp.feature.mapper

import com.jperez.timerapp.domain.model.Category
import com.jperez.timerapp.feature.model.CategoryColor
import com.jperez.timerapp.feature.model.CategoryType
import com.jperez.timerapp.feature.model.CategoryUI

class CategoryUIMapper {

    fun mapCategoryToCategoryUI(category: Category): CategoryUI {
        return CategoryUI(
            id = category.uid,
            name = category.name,
            color = CategoryColor.valueOf(category.color),
            categoryType = CategoryType.valueOf(category.type),
        )
    }

    fun reverseMapCategoryUIToCategory(category: CategoryUI): Category {
        return Category(
            uid = category.id,
            name = category.name,
            type = category.categoryType.name,
            color = category.color.name,
        )
    }
}