package com.jperez.timerapp.data.datasource

import com.jperez.timerapp.TestMockConstant
import com.jperez.timerapp.data.database.EntryDAO
import com.jperez.timerapp.domain.mappers.EntryEntityModelMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

/**
 * Unit tests for the EntryLocalDataSource class.
 */
class EntryLocalDataSourceTest : KoinTest {
    private lateinit var mockEntryDao: EntryDAO
    private lateinit var mockEntryEntityModelMapper: EntryEntityModelMapper
    private lateinit var localDataSource: EntryLocalDataSource

    @Before
    fun setUp() {
        mockEntryDao = mockk(relaxed = true)
        mockEntryEntityModelMapper = mockk(relaxed = true)
        localDataSource = EntryLocalDataSourceImpl()

        startKoin {
            modules(
                module {
                    single<EntryDAO> { mockEntryDao }
                    single<EntryEntityModelMapper> { mockEntryEntityModelMapper}
                })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getEntriesFromDatabase call dao`() = runTest {
        coEvery { mockEntryDao.getAll() } returns listOf(TestMockConstant.entryEntity)
        coEvery { mockEntryEntityModelMapper.mapEntityListToModelList(any()) } returns listOf(TestMockConstant.outputEntry)

        val result = localDataSource.getEntriesFromDatabase()

        assertTrue(result.isNotEmpty())
        coVerify(exactly = 1) {
            mockEntryDao.getAll()
        }
    }

    @Test
    fun `saveEntryToDatabase call dao`() = runTest {
        coEvery {
            mockEntryDao.insertAll(any())
        } answers {}

        val result = localDataSource.saveEntryToDatabase(
            entry = TestMockConstant.outputEntry
        )

        assertTrue(result.uid != null)
        coVerify(exactly = 1) {
            mockEntryDao.insertAll(any())
        }
    }

    @Test
    fun `deleteEntry call dao`() = runTest {
        coEvery {
            mockEntryDao.deleteByID("id")
        } answers {}

        localDataSource.deleteEntry(
            id = "id"
        )

        coVerify(exactly = 1) {
            mockEntryDao.deleteByID("id")
        }
    }
}