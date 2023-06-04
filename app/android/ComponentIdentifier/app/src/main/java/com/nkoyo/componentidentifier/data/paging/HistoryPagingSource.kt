package com.nkoyo.componentidentifier.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.nkoyo.componentidentifier.database.HistoryDao
import com.nkoyo.componentidentifier.database.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryPagingSource @Inject constructor(
    private val historyDao: HistoryDao,
) {

    fun showHistory(pageSize: Int): Flow<PagingData<HistoryEntity>> = pagingHistory(pageSize) {
        historyDao.showHistoryAsPaged()
    }

    private fun pagingHistory(
        pageSize: Int,
        block: () -> PagingSource<Int, HistoryEntity>
    ): Flow<PagingData<HistoryEntity>> =
        Pager(PagingConfig(pageSize)) { block() }.flow
}