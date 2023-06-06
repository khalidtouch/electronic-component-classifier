package com.nkoyo.componentidentifier.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerHistory(historyEntity: HistoryEntity)

    @Delete
    suspend fun deleteHistory(historyEntity: HistoryEntity)

    @Query("delete from HistoryEntity")
    suspend fun clearHistory()

    @Query(
        value = "select * from HistoryEntity where componentName like :componentName"
    )
    fun findByComponentName(componentName: String): Flow<List<HistoryEntity>>

    @Query(
        value = "select * from HistoryEntity where historyId like :id"
    )
    fun findByComponentId(id : Long): Flow<HistoryEntity?>

    @Query("select * from HistoryEntity order by dateTime desc")
    fun showHistory(): Flow<List<HistoryEntity>>

    @Query(
        value = "select * from HistoryEntity where componentName like :componentName"
    )
    fun findByComponentNameAsPaged(componentName: String): PagingSource<Int, HistoryEntity>

    @Query("select * from HistoryEntity order by dateTime desc")
    fun showHistoryAsPaged(): PagingSource<Int, HistoryEntity>

}