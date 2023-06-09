package com.nkoyo.componentidentifier.datastore

import androidx.datastore.core.DataStore
import com.nkoyo.componentidentifier.DarkThemeConfigProto
import com.nkoyo.componentidentifier.UserPreferences
import com.nkoyo.componentidentifier.copy
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NkPreferenceDataSource @Inject constructor(
   private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserData(
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM ->
                        DarkThemeConfig.FOLLOW_SYSTEM

                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                }
            )
        }

    suspend fun updateDarkThemeConfig(config: DarkThemeConfig) {
        userPreferences.updateData { pref ->
            pref.copy {
                this.darkThemeConfig = when(config) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM

                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }
}