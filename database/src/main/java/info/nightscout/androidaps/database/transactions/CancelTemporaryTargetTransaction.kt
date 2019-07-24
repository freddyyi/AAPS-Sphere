package info.nightscout.androidaps.database.transactions

import info.nightscout.androidaps.database.AppRepository

class CancelTemporaryTargetTransaction(val timestamp: Long = System.currentTimeMillis()) : Transaction<Unit>() {

    override fun run() {
        val currentlyActive = AppRepository.database.temporaryTargetDao.getTemporaryTargetActiveAt(timestamp)
                ?: throw IllegalStateException("There is currently no TemporaryTarget active.")
        currentlyActive.duration = timestamp - currentlyActive.timestamp
        AppRepository.database.temporaryTargetDao.updateExistingEntry(currentlyActive)
        changes.add(currentlyActive)
    }
}