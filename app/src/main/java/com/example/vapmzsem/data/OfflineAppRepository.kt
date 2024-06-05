package com.example.vapmzsem.data

import kotlinx.coroutines.flow.Flow
import java.util.Date

class OfflineAppRepository(private val fuelingDao : FuelingDao, private val routeDao: RouteDao) : AppRepository {
    override suspend fun insert(item: Fueling) {
        fuelingDao.insert(item)
    }

    override suspend fun insert(item: Route) {
        val corespondingFuelingId : Int? = findCorespondingFueling(item.finish_time)
        val fuelingToInser = item.copy(id_F = corespondingFuelingId)
        routeDao.insert(fuelingToInser)
    }
    suspend fun insertRaw(item: Route){
        routeDao.insert(item)
    }

    override suspend fun update(item: Fueling) {
        fuelingDao.update(item)
    }

    override suspend fun update(item: Route) {
        routeDao.update(item)
    }

    override suspend fun delete(item: Fueling) {
        fuelingDao.delete(item)
    }

    override suspend fun delete(item: Route) {
        routeDao.delete(item)
    }

    override fun getFueling(id: Int): Flow<Fueling?>{
        return fuelingDao.getFueling(id)
    }

    override fun getAllFuelings(): Flow<List<Fueling>> {
        return fuelingDao.getAllFuelings()
    }

    override fun getNewestFueling(): Flow<Fueling?> {
        return fuelingDao.getNewestFueling()
    }

    override fun getRoute(id: Int): Flow<Route> {
        return routeDao.getRoute(id)
    }

    override fun getAllRoutes(): Flow<List<Route>> {
        return routeDao.getAllRoutes()
    }

    override fun getAllRoutesToFueling(id: Int): Flow<List<Route>> {
        return  routeDao.getAllRoutesToFueling(id)
    }

    override fun getNewestRoute(): Flow<Route?> {
        return routeDao.getNewestRoute()
    }

    override fun findCorespondingFueling(time: Date): Int? {
        return fuelingDao.findCorespondingFueling(time)
    }
}