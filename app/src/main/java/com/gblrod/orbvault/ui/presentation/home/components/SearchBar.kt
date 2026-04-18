package com.gblrod.orbvault.ui.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import kotlinx.coroutines.launch

@Composable
fun SearchBar(
    countryQuery: String,
    onCountryQuery: (String) -> Unit,
    onSearch: () -> Unit
) {
    val maxChar = 32
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = countryQuery,
        onValueChange = {
            if (it.length <= maxChar) {
                onCountryQuery(it)
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.outlined_text_field_placeholder),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                scope.launch {
                    onSearch()
                    keyboardController?.hide()
                    focus.clearFocus(force = true)
                }
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Flag,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            if (countryQuery.isNotBlank()) {
                IconButton(
                    onClick = {
                        onCountryQuery("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}