package com.example.vapmzsem.data

import android.content.Context

interface AppContainer{
    val appRepository : AppRepository
}

class AppDataContainer(private val context: Context) : AppContainer{

    override val appRepository: AppRepository by lazy {
        val appDatabase = AppDatabase.getDatabase(context)
        OfflineAppRepository(appDatabase.fuelingDao(), appDatabase.routesDao() )
    }

}