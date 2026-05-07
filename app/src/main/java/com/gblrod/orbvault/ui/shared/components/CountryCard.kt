package com.gblrod.orbvault.ui.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CountryCard(
    flag: String?,
    nameCommon: String,
    nameOfficial: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    index: Int? = null,
    infoContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = flag,
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TopList(
                        country = nameCommon,
                        official = nameOfficial,
                        index = index
                    )
                }
                FavoriteButton(
                    isFavorite = isFavorite,
                    modifier = Modifier.align(Alignment.Top),
                    onClick = { onFavoriteClick() }
                )
            }

            if (infoContent != null) {

                Spacer(modifier = Modifier.height(4.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(4.dp))

                infoContent()
            }
        }
    }
}