package com.nkoyo.componentidentifier.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nkoyo.componentidentifier.R


@Composable
fun NkSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    fullWidth: Dp,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
        cursorColor = MaterialTheme.colorScheme.onPrimary,
    )
) {
    Box(
        contentAlignment = Alignment.CenterStart, modifier = Modifier
            .padding(8.dp)
            .background(color = Color.White)
            .width(fullWidth.times(0.3f))
    ) {
        Box {
            Icon(
                painterResource(id = R.drawable.icon_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Box(
            Modifier
                .padding(start = 24.dp)
                .fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_records),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                singleLine = true,
                maxLines = 1,
                colors = colors,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                )
            )
        }
    }
}


@Composable
@Preview
private fun NkSearchBarPreview() {
    NkSearchBar(
        fullWidth = 400.dp,
        value = "",
        onValueChange = {}
    )
}