package com.nkoyo.componentidentifier.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nkoyo.componentidentifier.ui.theme.LocalBackgroundTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NkSimpleTopBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = LocalBackgroundTheme.current.surface,
        scrolledContainerColor = LocalBackgroundTheme.current.surface,
        navigationIconContentColor = LocalBackgroundTheme.current.outline,
        titleContentColor = LocalBackgroundTheme.current.outline,
        actionIconContentColor = LocalBackgroundTheme.current.outline,
    ),
    title: @Composable () -> Unit,
    navIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navIcon,
        actions = actions,
        colors = colors,
    )
}