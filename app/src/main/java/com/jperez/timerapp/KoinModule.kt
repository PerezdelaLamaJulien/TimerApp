package com.jperez.timerapp

import androidx.room.Room
import com.jperez.timerapp.database.AppDatabase
import com.jperez.timerapp.database.EntryDAO
import com.jperez.timerapp.datasource.EntryLocalDataSource
import com.jperez.timerapp.datasource.EntryLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


var koinModule = module {
    viewModel<MainViewModel> {
        MainViewModel()
    }

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

    factory<EntryLocalDataSource> {
        EntryLocalDataSourceImpl()
    }

    factory<EntryUIMapper> {
        EntryUIMapper()
    }
}