package org.d3if3062.mobpro1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3062.mobpro1.model.Laundry

@Dao
interface laundryShopDao {

    @Insert
    suspend fun insert(laundry: Laundry)

    @Update
    suspend fun update(laundry: Laundry)

    //Order by item id/order by first item in the list
    @Query("SELECT * FROM Laundry ORDER BY id")
    fun getLaundryItem(): Flow<List<Laundry>>

    @Query("SELECT * FROM Laundry WHERE id = :id")
    suspend fun getLaundryItemById(id: Long): Laundry?

    @Query("DELETE FROM Laundry WHERE id = :id")
    suspend fun deleteLaundryOrdersById(id: Long)
}