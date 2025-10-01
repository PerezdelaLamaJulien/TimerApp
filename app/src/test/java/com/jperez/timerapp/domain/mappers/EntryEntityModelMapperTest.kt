package com.jperez.timerapp.domain.mappers

import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.data.database.CategoryDAO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class EntryEntityModelMapperTest {
    val mapper = EntryEntityModelMapper()
    private lateinit var mockCategoryDAO: CategoryDAO
    private lateinit var mockCategoryEntityModelMapper: CategoryEntityModelMapper

    @Before
    fun setUp() {
        mockCategoryEntityModelMapper = mockk(relaxed = true)
        mockCategoryDAO = mockk(relaxed = true)

        startKoin {
            modules(
                module {
                    single<CategoryEntityModelMapper> { mockCategoryEntityModelMapper }
                    single<CategoryDAO> { mockCategoryDAO}
                })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun mapEntityListToModelList() = runTest {
        coEvery { mockCategoryDAO.getAll() } returns listOf(TestMockConstant.categoryEntity)
        coEvery { mockCategoryEntityModelMapper.mapEntityToModel(any()) } returns TestMockConstant.outputCategory

        val result = mapper.mapEntityListToModelList(
            listOf(TestMockConstant.entryEntity)
        )
        assertEquals(
            TestMockConstant.outputEntry, result.first()
        )
    }

    @Test
    fun mapEntityToModel() {
        coEvery { mockCategoryEntityModelMapper.mapEntityToModel(any()) } returns TestMockConstant.outputCategory

        val result = mapper.mapEntityToModel(
            TestMockConstant.entryEntity,
            TestMockConstant.categoryEntity
        )
        assertEquals(TestMockConstant.outputEntry, result)
    }

}