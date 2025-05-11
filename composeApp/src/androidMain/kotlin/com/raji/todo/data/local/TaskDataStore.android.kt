package com.raji.todo.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return createDataStore {
        (context as Context).filesDir.resolve(dataStoreFileName).absolutePath
    }
}