package com.jperez.timerapp.domain.mappers

import com.jperez.timerapp.TestMockConstant
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryEntityModelMapperTest {

    val mapper = CategoryEntityModelMapper()


    @Test
    fun mapEntityListToModelList() {
        val result = mapper.mapEntityListToModelList(
            listOf(TestMockConstant.categoryEntity)
        )
        assertEquals(
            TestMockConstant.outputCategory, result.first()
        )
    }

    @Test
    fun mapEntityToModel() {
        val result = mapper.mapEntityToModel(
            TestMockConstant.categoryEntity
        )
        assertEquals(
            TestMockConstant.outputCategory, result
        )
    }

}