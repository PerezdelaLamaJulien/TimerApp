package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
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

class GetCategoriesUseCaseTest {

    private lateinit var mockCategoryLocalDataSource: CategoryLocalDataSource
    private val useCase = GetCategoriesUseCase()

    @Before
    fun setUp() {
        mockCategoryLocalDataSource = mockk(relaxed = true)
        startKoin {
            modules(
                module {
                    single<CategoryLocalDataSource> { mockCategoryLocalDataSource }
                })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun execute() = runTest(UnconfinedTestDispatcher()) {
        coEvery { mockCategoryLocalDataSource.getCategoriesFromDatabase() } returns listOf(TestMockConstant.outputCategory)
        val result = useCase.execute()
        assertEquals(TestMockConstant.outputCategory, result.first())
    }
}
