package com.example.vapmzsem.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vapmzsem.ui.Fueling.FuelingAsUi
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface FuelingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Fueling)

    @Update
    suspend fun update(item: Fueling)

    @Delete
    suspend fun delete(item: Fueling)

    @Query("Select * from fuelings Where id_F = :id")
    fun getFueling(id : Int) : Flow<Fueling>

    @Query("Select * from fuelings Order by time DESC")
    fun getAllFuelings() : Flow<List<Fueling>>

    @Query("Select * from fuelings where time in (select max(time) from fuelings)")
    fun getNewestFueling() : Flow<Fueling?>

    @Query("Select id_F from fuelings where time in (select max(time) from fuelings where :finishTime >= time)")
    suspend fun findCorespondingFueling(finishTime : Date) : Int?

    @Query("Select * from fuelings where id_F = :id")
    suspend fun findCorespondingFueling(id:Int?) : Fueling?

}

@Dao
interface RouteDao{
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : Route)

    @Update
    suspend fun update(item: Route)

    @Delete
    suspend fun delete(item : Route)

    @Query("Select * from routes Where id_R = :id")
    fun getRoute(id : Int) : Flow<Route>

    @Query("Select * from routes order by start_time DESC")
    fun getAllRoutes() : Flow<List<Route>>

    @Query("Select * from routes Where id_F = :id")
    fun getAllRoutesToFueling(id:Int) : Flow<List<Route>>

    @Query("Select * from routes where finish_time in (Select max(finish_time) from routes)")
    fun getNewestRoute() : Flow<Route?>


}