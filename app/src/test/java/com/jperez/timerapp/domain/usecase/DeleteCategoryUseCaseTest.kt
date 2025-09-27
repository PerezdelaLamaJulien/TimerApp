package com.jperez.timerapp.domain.usecase

import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class DeleteCategoryUseCaseTest {

    private lateinit var mockCategoryLocalDataSource: CategoryLocalDataSource
    private val useCase = DeleteCategoryUseCase()

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
        coEvery { mockCategoryLocalDataSource.deleteCategory("deleteId") } answers {}
        useCase.execute(
            categoryId = "deleteId"
        )
        coVerify { mockCategoryLocalDataSource.deleteCategory("deleteId") }
    }
}