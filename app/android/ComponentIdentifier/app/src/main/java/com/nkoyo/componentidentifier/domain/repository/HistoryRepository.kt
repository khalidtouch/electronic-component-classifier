package com.nkoyo.componentidentifier.domain.repository

import androidx.paging.PagingData
import com.nkoyo.componentidentifier.database.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun showHistoryAsPaged(pageSize: Int): Flow<PagingData<HistoryEntity>>

    suspend fun registerHistory(historyEntity: HistoryEntity)

    suspend fun deleteHistory(historyEntity: HistoryEntity)

    suspend fun clearHistory()

    fun findByComponentName(componentName: String): Flow<List<HistoryEntity>>

    fun findByComponentId(id : Long): Flow<HistoryEntity?>

    fun showHistory(): Flow<List<HistoryEntity>>

}