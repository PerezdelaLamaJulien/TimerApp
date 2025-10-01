package com.jperez.timerapp.feature.mapper

import com.jperez.timerapp.TestMockConstant
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the CategoryUIMapper class.
 */
class CategoryUIMapperTest {

    val mapper = CategoryUIMapper()

    @Test
    fun `map Category to CategoryUI`() = runTest {
        val result = mapper.mapCategoryToCategoryUI(TestMockConstant.outputCategory)
        assertEquals(TestMockConstant.categoryUI, result
        )
    }

    @Test
    fun `map CategoryUI to Category`() = runTest {
        val result = mapper.reverseMapCategoryUIToCategory(TestMockConstant.categoryUI)
        assertEquals(TestMockConstant.outputCategory, result
        )
    }
}