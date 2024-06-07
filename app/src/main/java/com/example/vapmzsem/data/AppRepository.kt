package com.example.vapmzsem.data

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AppRepository {
    suspend fun insert(item: Fueling)
    suspend fun update(item: Fueling)
    suspend fun delete(item: Fueling)
    fun getFueling(id : Int) : Flow<Fueling?>
    fun getAllFuelings() : Flow<List<Fueling>>
    fun getNewestFueling() : Flow<Fueling?>
    suspend fun insert(item : Route)
    suspend fun update(item: Route)
    suspend fun delete(item : Route)
    fun getRoute(id : Int) : Flow<Route>
    fun getAllRoutes() : Flow<List<Route>>
    fun getAllRoutesToFueling(id:Int) : Flow<List<Route>>
    fun getNewestRoute() : Flow<Route?>
    suspend fun findCorespondingFueling(time : Date) : Int?
    suspend fun findCorespondingFueling(id : Int?) : Fueling?
    fun calculateAverageFuelConsumption() : Flow<Float>
}