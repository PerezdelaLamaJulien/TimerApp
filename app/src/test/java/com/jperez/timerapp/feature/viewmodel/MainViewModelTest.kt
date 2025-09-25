package com.jperez.timerapp.feature.viewmodel

import com.jperez.timerapp.MainCoroutineRule
import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.domain.usecase.AddEntryUseCase
import com.jperez.timerapp.domain.usecase.GetEntriesUseCase
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
    private lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        mockAddEntryUseCase = mockk(relaxed = true)
        mockGetEntriesUseCase = mockk(relaxed = true)
        mockEntryUIMapper = mockk(relaxed = true)
        viewModel = MainViewModel()
        startKoin {
            modules(
                module {
                    single<AddEntryUseCase> { mockAddEntryUseCase }
                    single<GetEntriesUseCase> { mockGetEntriesUseCase }
                    single<EntryUIMapper> { mockEntryUIMapper }
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
            assertEquals(TestMockConstant.entryUI, viewModel.uiState.value.first())
        }

    @Test
    fun getEntries() =
        runTest(UnconfinedTestDispatcher()) {

            coEvery { mockGetEntriesUseCase.execute() } returns listOf(TestMockConstant.outputEntry)
            coEvery { mockEntryUIMapper.mapEntryToEntryUI(TestMockConstant.outputEntry) } returns TestMockConstant.entryUI
            viewModel.getEntries()

            advanceUntilIdle() // Yields to perform the registrations
            assertEquals(TestMockConstant.entryUI, viewModel.uiState.value.first())
        }
}
