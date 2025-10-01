package com.jperez.timerapp.feature.viewmodel

import com.jperez.timerapp.MainCoroutineRule
import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.domain.usecase.AddEntryUseCase
import com.jperez.timerapp.domain.usecase.GetCategoriesUseCase
import com.jperez.timerapp.domain.usecase.GetEntriesUseCase
import com.jperez.timerapp.feature.mapper.CategoryUIMapper
import com.jperez.timerapp.feature.mapper.EntryUIMapper
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
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

/**
 * Unit test for [com.jperez.timerapp.feature.viewmodel.MainViewModel].
 */

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : KoinTest {
    private lateinit var mockAddEntryUseCase: AddEntryUseCase
    private lateinit var mockGetEntriesUseCase: GetEntriesUseCase

    private lateinit var mockEntryUIMapper: EntryUIMapper

    private lateinit var mockGetCategoriesUseCase: GetCategoriesUseCase

    private lateinit var mockCategoryUIMapper: CategoryUIMapper
    private lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        mockAddEntryUseCase = mockk(relaxed = true)
        mockGetEntriesUseCase = mockk(relaxed = true)
        mockEntryUIMapper = mockk(relaxed = true)
        mockGetCategoriesUseCase = mockk(relaxed = true)
        mockCategoryUIMapper = mockk(relaxed = true)
        viewModel = MainViewModel()
        startKoin {
            modules(
                module {
                    single<AddEntryUseCase> { mockAddEntryUseCase }
                    single<GetEntriesUseCase> { mockGetEntriesUseCase }
                    single<EntryUIMapper> { mockEntryUIMapper }
                    single<GetCategoriesUseCase> { mockGetCategoriesUseCase }
                    single<CategoryUIMapper> { mockCategoryUIMapper }
                })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun registerTimer() =
        runTest(UnconfinedTestDispatcher()) {
            coEvery { mockAddEntryUseCase.execute(any()) } returns TestMockConstant.outputEntry
            coEvery { mockEntryUIMapper.mapEntryToEntryUI(TestMockConstant.outputEntry) } returns TestMockConstant.entryUI

            viewModel.registerTimer(
                launchedDateTime = LocalDateTime.of(2025, 1, 15, 12, 30),
                duration = 5.seconds.toJavaDuration()
            )

            advanceUntilIdle() // Yields to perform the registrations
            assertEquals(TestMockConstant.entryUI, viewModel.uiState.value.entries.first())
        }

    @Test
    fun initScreenState() =
        runTest(UnconfinedTestDispatcher()) {

            coEvery { mockGetEntriesUseCase.execute() } returns listOf(TestMockConstant.outputEntry)
            coEvery { mockGetCategoriesUseCase.execute() } returns listOf(TestMockConstant.outputCategory)
            coEvery { mockEntryUIMapper.mapEntryToEntryUI(TestMockConstant.outputEntry) } returns TestMockConstant.entryUI
            coEvery { mockCategoryUIMapper.mapCategoryToCategoryUI(TestMockConstant.outputCategory) } returns TestMockConstant.categoryUI
            viewModel.initScreenState()

            advanceUntilIdle() // Yields to perform the registrations
            assertEquals(TestMockConstant.entryUI, viewModel.uiState.value.entries.first())
            assertEquals(TestMockConstant.categoryUI, viewModel.uiState.value.categories.first())
        }

    @Test
    fun selectedCategoryChanged() =
        runTest(UnconfinedTestDispatcher()) {
            coEvery { mockGetEntriesUseCase.execute() } returns listOf(TestMockConstant.outputEntry)
            coEvery { mockGetCategoriesUseCase.execute() } returns listOf(TestMockConstant.outputCategory)
            coEvery { mockEntryUIMapper.mapEntryToEntryUI(TestMockConstant.outputEntry) } returns TestMockConstant.entryUI
            coEvery { mockCategoryUIMapper.mapCategoryToCategoryUI(TestMockConstant.outputCategory) } returns TestMockConstant.categoryUI

            viewModel.selectedCategoryChanged(TestMockConstant.updatedCategoryUI)

            advanceUntilIdle() // Yields to perform the registrations
            assertEquals(TestMockConstant.categoryUI, viewModel.uiState.value.selectedCategory)
        }
}
