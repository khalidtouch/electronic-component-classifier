package com.nkoyo.componentidentifier.data.repository

import androidx.paging.PagingData
import com.nkoyo.componentidentifier.data.paging.HistoryPagingSource
import com.nkoyo.componentidentifier.database.HistoryDao
import com.nkoyo.componentidentifier.database.HistoryEntity
import com.nkoyo.componentidentifier.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyPagerSource: HistoryPagingSource,
    private val historyDao: HistoryDao,
): HistoryRepository {

    override fun showHistoryAsPaged(pageSize: Int): Flow<PagingData<HistoryEntity>> {
        return historyPagerSource.showHistory(pageSize = pageSize)
    }

    override suspend fun registerHistory(historyEntity: HistoryEntity) {
        historyDao.registerHistory(historyEntity)
    }

    override suspend fun deleteHistory(historyEntity: HistoryEntity) {
       historyDao.deleteHistory(historyEntity)
    }

    override suspend fun clearHistory() {
       historyDao.clearHistory()
    }

    override fun findByComponentName(componentName: String): Flow<List<HistoryEntity>> {
       return historyDao.findByComponentName(componentName)
    }

    override fun showHistory(): Flow<List<HistoryEntity>> {
        return historyDao.showHistory()
    }
}