package com.talangor.app.data.local.seed

import com.talangor.app.data.local.dao.ActionDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object InitialActionSeeder {
    private val seedScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun seedIfNeeded(actionDao: ActionDao) {
        seedScope.launch {
            if (actionDao.countActions() == 0) {
                actionDao.insertAll(InitialActionSeeds.actions)
            }
            actionDao.capLongDurations()
        }
    }
}
