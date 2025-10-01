package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.data.datasource.EntryLocalDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class AddEntryUseCaseTest {
    private lateinit var mockEntryLocalDataSource: EntryLocalDataSource
    private val useCase = AddEntryUseCase()

    @Before
    fun setUp() {
        mockEntryLocalDataSource = mockk(relaxed = true)
        startKoin {
            modules(
                module {
                    single<EntryLocalDataSource> { mockEntryLocalDataSource }
                })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun execute() = runTest(UnconfinedTestDispatcher()) {
        coEvery { mockEntryLocalDataSource.saveEntryToDatabase(TestMockConstant.inputEntry) } returns TestMockConstant.outputEntry
        val result = useCase.execute(entry = TestMockConstant.inputEntry)
        assertEquals(TestMockConstant.outputEntry, result)
    }
}