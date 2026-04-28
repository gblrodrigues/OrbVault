package com.gblrod.orbvault.ui.countries.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

object MapOpener {
    fun openMap(
        context: Context,
        countryName: String
    ) {
        val uri = "geo:0,0?q=${Uri.encode(countryName)}".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}