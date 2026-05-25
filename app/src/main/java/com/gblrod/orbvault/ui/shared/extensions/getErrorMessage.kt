package com.gblrod.orbvault.ui.shared.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun getErrorMessage(
    showDetailedError: Boolean,
    genericErrorResId: Int,
    messageResId: Int,
    code: Int? = null
): String {
    return when {
        !showDetailedError -> stringResource(id = genericErrorResId)
        code != null -> stringResource(id = messageResId, code)
        else -> stringResource(id = messageResId)
    }
}