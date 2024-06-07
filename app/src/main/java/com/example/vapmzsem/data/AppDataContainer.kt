package com.example.vapmzsem.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface AppContainer{
    val appRepository : AppRepository
    var average_fuel_consumption : Flow<Float>
}

class AppDataContainer(private val context: Context) : AppContainer{

    override val appRepository: AppRepository by lazy {
        val appDatabase = AppDatabase.getDatabase(context)
        OfflineAppRepository(appDatabase.fuelingDao(), appDatabase.routesDao() )
    }
    override var average_fuel_consumption: Flow<Float> = appRepository.calculateAverageFuelConsumption()
}