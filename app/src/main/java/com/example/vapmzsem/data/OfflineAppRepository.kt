package com.example.vapmzsem.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Date

class OfflineAppRepository(private val fuelingDao : FuelingDao, private val routeDao: RouteDao) : AppRepository {
    override suspend fun insert(item: Fueling) {
        fuelingDao.insert(item)
        fixIntegrity()

        recountDistanceTraveledAndFuelConsumption()
    }

    override suspend fun insert(item: Route) {
        val corespondingFuelingId : Int? = findCorespondingFueling(item.finish_time)
        val fuelingToInser = item.copy(id_F = corespondingFuelingId)
        routeDao.insert(fuelingToInser)

        if(corespondingFuelingId != null) {
            val fueling = findCorespondingFueling(corespondingFuelingId)
            recountDistanceTraveledAndFuelConsumption(fueling!!)
        }
    }
    suspend fun insertRaw(item: Route){
        routeDao.insert(item)
    }

    override suspend fun update(item: Fueling) {
        fuelingDao.update(item)
        fixIntegrity()
        recountDistanceTraveledAndFuelConsumption()
    }

    override suspend fun update(item: Route) {
        val corespondingFuelingId : Int? = findCorespondingFueling(item.finish_time)
        val routeToUpdate = item.copy(id_F = corespondingFuelingId)
        routeDao.update(routeToUpdate)

        recountDistanceTraveledAndFuelConsumption()
    }
    suspend fun updateRaw(item : Route){
        routeDao.update(item)
    }

    override suspend fun delete(item: Fueling) {
        val routesToFueling = getAllRoutesToFueling(item.id_F).first()
        for(route in routesToFueling){
            val disconectedRoute = route.copy(id_F = null)
            updateRaw(disconectedRoute)
        }
        fuelingDao.delete(item)
        if (routesToFueling.isNotEmpty()) {
            val newCorespondingFueling =
                findCorespondingFueling(routesToFueling.first().finish_time)
            for (route in routesToFueling) {
                val updatedroute = route.copy(id_F = newCorespondingFueling)
                updateRaw(updatedroute)
            }
            if(newCorespondingFueling != null) {
                val fueling = getFueling(newCorespondingFueling).first()
                recountDistanceTraveledAndFuelConsumption(fueling!!)
            }
        }

    }
    suspend fun deleteRaw(item: Fueling) {
        fuelingDao.delete(item)
    }

    override suspend fun delete(item: Route) {
        routeDao.delete(item)

        if (item.id_F != null) {
            val fueling = getFueling(item.id_F).first()
            recountDistanceTraveledAndFuelConsumption(fueling!!)
        }
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

    override suspend fun findCorespondingFueling(time: Date): Int? {
        return fuelingDao.findCorespondingFueling(time)
    }

    override suspend fun findCorespondingFueling(id: Int?): Fueling? {
        return fuelingDao.findCorespondingFueling(id)
    }

    override fun calculateAverageFuelConsumption(): Flow<Float> {
        return getAllFuelings().map {
            fuelings ->
            val validFueling = fuelings.drop(1).filter {it.fuel_consumption.isFinite()}
            if (validFueling.isEmpty()) Float.POSITIVE_INFINITY else validFueling.map { it.fuel_consumption }.average().toFloat()
        }
    }

    suspend fun fixIntegrity(){
        val routes = getAllRoutes().first()

        for (route in routes){
            val fixedId_F = findCorespondingFueling(route.finish_time)
            if(route.id_F != fixedId_F){
                val updatedRoute = route.copy(id_F = fixedId_F)
                updateRaw(updatedRoute)
            }
        }
    }
    suspend fun recountDistanceTraveledAndFuelConsumption(fueling: Fueling){

            val routesCorespondingToFueling = routeDao.getAllRoutesToFueling(fueling.id_F).first()
            var distanceTraveled = 0f
            routesCorespondingToFueling.forEach { route -> distanceTraveled += route.distance }

            val averageFC =
                if (distanceTraveled == 0f) Float.POSITIVE_INFINITY else (fueling.quantity / distanceTraveled) * 100

            fuelingDao.update(
                fueling.copy(
                    distance_traveled = distanceTraveled,
                    fuel_consumption = averageFC
                )
            )

    }
    suspend fun recountDistanceTraveledAndFuelConsumption(){
        val fuelings = getAllFuelings().first()

        for (fueling in fuelings){
            recountDistanceTraveledAndFuelConsumption(fueling)
        }
    }

}