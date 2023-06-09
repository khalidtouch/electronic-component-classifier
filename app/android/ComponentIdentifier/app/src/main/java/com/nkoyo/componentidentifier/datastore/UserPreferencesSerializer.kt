package com.nkoyo.componentidentifier.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.nkoyo.componentidentifier.UserPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserPreferencesSerializer @Inject constructor(): Serializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            @Suppress("BlockingMethodInNonBlockingContext")
            UserPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        @Suppress("BlockingMethodInNonBlockingContext")
        t.writeTo(output)
    }

}