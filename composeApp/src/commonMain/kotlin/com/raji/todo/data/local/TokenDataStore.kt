package com.raji.todo.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath


// shared/src/androidMain/kotlin/createDataStore.kt

/**
 * Gets the singleton DataStore instance, creating it if necessary.
 */
fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "tasks.preferences_pb"

// shared/src/androidMain/kotlin/createDataStore.android.kt

expect fun createDataStore(context:Any?): DataStore<Preferences>
