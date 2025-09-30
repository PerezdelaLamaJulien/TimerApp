package com.jperez.timerapp

import androidx.room.Room
import com.jperez.timerapp.data.database.AppDatabase
import com.jperez.timerapp.data.database.CategoryDAO
import com.jperez.timerapp.data.database.EntryDAO
import com.jperez.timerapp.data.datasource.CategoryLocalDataSource
import com.jperez.timerapp.data.datasource.CategoryLocalDataSourceImpl
import com.jperez.timerapp.data.datasource.EntryLocalDataSource
import com.jperez.timerapp.data.datasource.EntryLocalDataSourceImpl
import com.jperez.timerapp.domain.mappers.CategoryEntityModelMapper
import com.jperez.timerapp.domain.mappers.EntryEntityModelMapper
import com.jperez.timerapp.domain.usecase.SaveCategoryUseCase
import com.jperez.timerapp.domain.usecase.AddEntryUseCase
import com.jperez.timerapp.domain.usecase.DeleteCategoryUseCase
import com.jperez.timerapp.domain.usecase.GetCategoriesUseCase
import com.jperez.timerapp.domain.usecase.GetEntriesUseCase
import com.jperez.timerapp.feature.mapper.CategoryUIMapper
import com.jperez.timerapp.feature.mapper.EntryUIMapper
import com.jperez.timerapp.feature.viewmodel.MainViewModel
import com.jperez.timerapp.feature.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


var koinModule = module {

    // Feature
    viewModel<MainViewModel> {
        MainViewModel()
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel()
    }

    factory<EntryUIMapper> {
        EntryUIMapper()
    }

    factory<CategoryUIMapper> {
        CategoryUIMapper()
    }
    // Domain

    factory<CategoryEntityModelMapper> {
        CategoryEntityModelMapper()
    }

    factory<EntryEntityModelMapper> {
        EntryEntityModelMapper()
    }

    factory<SaveCategoryUseCase> {
        SaveCategoryUseCase()
    }

    factory<AddEntryUseCase> {
        AddEntryUseCase()
    }

    factory<GetCategoriesUseCase> {
        GetCategoriesUseCase()
    }

    factory<GetEntriesUseCase> {
        GetEntriesUseCase()
    }

    factory<DeleteCategoryUseCase> {
        DeleteCategoryUseCase()
    }

    // Data
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "timer_db"
        ).build()
    }

    single<EntryDAO> {
        get<AppDatabase>().entryDAO()
    }

    single<CategoryDAO> {
        get<AppDatabase>().categoryDAO()
    }

    factory<EntryLocalDataSource> {
        EntryLocalDataSourceImpl()
    }

    factory<CategoryLocalDataSource> {
        CategoryLocalDataSourceImpl()
    }
}