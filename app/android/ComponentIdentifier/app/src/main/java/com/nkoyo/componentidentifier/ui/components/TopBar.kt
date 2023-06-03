package com.nkoyo.componentidentifier.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NkSimpleTopBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        scrolledContainerColor = MaterialTheme.colorScheme.surface,
        navigationIconContentColor = MaterialTheme.colorScheme.outline,
        titleContentColor = MaterialTheme.colorScheme.outline,
        actionIconContentColor = MaterialTheme.colorScheme.outline,
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