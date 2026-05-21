package com.gblrod.orbvault.ui.countries.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.BlueActions

@Composable
fun OrbVaultSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.outlined_text_field_placeholder),
    maxChar: Int = 32,
    leadingIcon: (@Composable (() -> Unit))? = null,
    onSearch: (() -> Unit)? = null,
    onClearSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = query,
        onValueChange = {
            if (it.length <= maxChar) {
                onQueryChanged(it)
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusRequester.freeFocus()
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
                onSearch?.invoke()
            },
        ),
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(
                    onClick = { onClearSearch() }
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
            focusedIndicatorColor = BlueActions,
        ),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )
}