package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.shared.components.SelectionAlertDialog

@Composable
fun ComparisonClearFields(
    onClearFields: () -> Unit,
    onDismiss: () -> Unit,
) {
    SelectionAlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.comparison_alert_dialog_title)
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.comparison_alert_dialog_description)
            )
        },
        onDismiss = { onDismiss() },
        onConfirm = {
            onClearFields()
            onDismiss()
        },
        confirmText = stringResource(id = R.string.dialog_action_confirm),
        dismissText = stringResource(id = R.string.dialog_action_dismiss)
    )
}