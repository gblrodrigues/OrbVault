package com.gblrod.orbvault.ui.countries.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
    onSearch: () -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    expanded: Boolean,
    onClose: () -> Unit,
    shouldRequestFocus: Boolean = false
) {
    val maxChar = 32
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(shouldRequestFocus) {
        if (shouldRequestFocus) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

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
            if (expanded) {
                IconButton(
                    onClick = { onClose() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.TravelExplore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
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
            .fillMaxWidth()
            .then(
                if (expanded) {
                    Modifier.padding(horizontal = 16.dp)
                } else {
                    Modifier.padding(16.dp)
                }
            )
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused && !expanded) {
                    onExpandedChange(true)
                }
            }
    )
}