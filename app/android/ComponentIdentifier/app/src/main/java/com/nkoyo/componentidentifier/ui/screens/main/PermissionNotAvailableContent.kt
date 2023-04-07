package com.nkoyo.componentidentifier.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionNotAvailableContent(
    modifier: Modifier = Modifier,
    cameraPermissionState: PermissionState,
) {
    Surface(modifier= modifier){
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {cameraPermissionState.launchPermissionRequest()},
                modifier = modifier.padding(16.dp)
            ){
                Text(
                    text = "Request permission"
                )
            }
        }
    }

}