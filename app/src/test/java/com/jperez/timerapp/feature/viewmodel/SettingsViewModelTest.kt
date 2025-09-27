package com.jperez.timerapp.feature.viewmodel

import com.jperez.timerapp.MainCoroutineRule
import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.domain.usecase.DeleteCategoryUseCase
import com.jperez.timerapp.domain.usecase.GetCategoriesUseCase
import com.jperez.timerapp.domain.usecase.SaveCategoryUseCase
import com.jperez.timerapp.feature.mapper.CategoryUIMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Unit test for [SettingsViewModel].
 */

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest : KoinTest {
    private lateinit var mockGetCategoriesUseCase: GetCategoriesUseCase
    private lateinit var mockSaveCategoryUseCase: SaveCategoryUseCase
    private lateinit var mockDeleteCategoryUseCase: DeleteCategoryUseCase
    private lateinit var mockCategoryUIMapper: CategoryUIMapper
    private lateinit var viewModel: SettingsViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        mockGetCategoriesUseCase = mockk(relaxed = true)
        mockSaveCategoryUseCase = mockk(relaxed = true)
        mockDeleteCategoryUseCase = mockk(relaxed = true)
        mockCategoryUIMapper = mockk(relaxed = true)
        viewModel = SettingsViewModel()
        startKoin {
            modules(
                module {
                    single<GetCategoriesUseCase> { mockGetCategoriesUseCase }
                    single<SaveCategoryUseCase> { mockSaveCategoryUseCase }
                    single<DeleteCategoryUseCase> { mockDeleteCategoryUseCase }
                    single<CategoryUIMapper> { mockCategoryUIMapper }
                })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun getCategories() =
        runTest(UnconfinedTestDispatcher()) {
            coEvery { mockGetCategoriesUseCase.execute() } returns listOf(TestMockConstant.outputCategory)
            coEvery { mockCategoryUIMapper.mapCategoryToCategoryUI(TestMockConstant.outputCategory) } returns TestMockConstant.categoryUI
            viewModel.getCategories()

            advanceUntilIdle()
            assertEquals(TestMockConstant.categoryUI, viewModel.uiState.value.first())
        }

    @Test
    fun saveNewCategory() =
        runTest(UnconfinedTestDispatcher()) {
            coEvery { mockGetCategoriesUseCase.execute() } returns listOf()
            viewModel.getCategories()
            advanceUntilIdle()

            coEvery { mockCategoryUIMapper.reverseMapCategoryUIToCategory(TestMockConstant.categoryUIWithoutId) } returns TestMockConstant.outputCategory
            coEvery { mockSaveCategoryUseCase.execute(TestMockConstant.outputCategory) } returns TestMockConstant.outputCategory
            viewModel.saveCategory(TestMockConstant.categoryUIWithoutId)

            advanceUntilIdle()
            assertEquals(TestMockConstant.categoryUIWithoutId, viewModel.uiState.value.first())
        }

    @Test
    fun saveUpdateCategory() =
        runTest(UnconfinedTestDispatcher()) {
            coEvery { mockGetCategoriesUseCase.execute() } returns listOf(TestMockConstant.outputCategory)
            coEvery { mockCategoryUIMapper.mapCategoryToCategoryUI(TestMockConstant.outputCategory) } returns TestMockConstant.categoryUI
            viewModel.getCategories()
            advanceUntilIdle()

            coEvery { mockCategoryUIMapper.reverseMapCategoryUIToCategory(TestMockConstant.categoryUIWithoutId) } returns TestMockConstant.outputCategory
            coEvery { mockSaveCategoryUseCase.execute(TestMockConstant.outputCategory) } returns TestMockConstant.outputCategory
            viewModel.saveCategory(TestMockConstant.categoryUIWithoutId)

            advanceUntilIdle()
            assertEquals(TestMockConstant.categoryUIWithoutId, viewModel.uiState.value.last())
        }
}
