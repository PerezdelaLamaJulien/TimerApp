package com.jperez.timerapp.data.datasource

import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.data.database.CategoryDAO
import com.jperez.timerapp.domain.mappers.CategoryEntityModelMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryLocalDataSourceTest {

    private lateinit var mockCategoryDao: CategoryDAO
    private lateinit var mockCategoryEntityModelMapper: CategoryEntityModelMapper

    val dataSource = CategoryLocalDataSourceImpl()

    @Before
    fun setUp() {
        mockCategoryDao = mockk(relaxed = true)
        mockCategoryEntityModelMapper = mockk(relaxed = true)

        startKoin {
            modules(
                module {
                    single<CategoryDAO> { mockCategoryDao }
                    single<CategoryEntityModelMapper> { mockCategoryEntityModelMapper }
                })
        }
    }


    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getCategoriesFromDatabase() = runTest(UnconfinedTestDispatcher()) {
        coEvery { mockCategoryDao.getAll() } returns listOf(TestMockConstant.categoryEntity)
        coEvery { mockCategoryEntityModelMapper.mapEntityListToModelList(any()) } returns listOf(TestMockConstant.outputCategory)

        val result = dataSource.getCategoriesFromDatabase()
        assertEquals(TestMockConstant.outputCategory, result.first())
    }

    @Test
    fun saveCategoryToDatabaseNewCategory() = runTest(UnconfinedTestDispatcher()) {
        coEvery { mockCategoryDao.insertCategory(any()) } answers {}
        dataSource.saveCategoryToDatabase(TestMockConstant.inputCategory)
        coVerify(exactly = 1) { mockCategoryDao.insertCategory(any()) }
    }

    @Test
    fun saveCategoryToDatabaseUpdateCategory() = runTest(UnconfinedTestDispatcher()) {
        coEvery { mockCategoryDao.updateCategory(any()) } answers {}
        dataSource.saveCategoryToDatabase(TestMockConstant.outputCategory)
        coVerify(exactly = 1) { mockCategoryDao.updateCategory(any()) }
    }


    @Test
    fun deleteCategory() = runTest(UnconfinedTestDispatcher()) {
        coEvery { mockCategoryDao.deleteByID(any()) } answers {}
        dataSource.deleteCategory("uid")
        coVerify(exactly = 1) { mockCategoryDao.deleteByID(any()) }
    }

}