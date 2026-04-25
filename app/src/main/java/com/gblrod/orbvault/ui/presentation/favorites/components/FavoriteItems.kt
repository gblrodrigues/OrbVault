package com.gblrod.orbvault.ui.presentation.favorites.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gblrod.orbvault.ui.presentation.explore.components.CountryBottomSheet
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.shared.components.TopList

@Composable
fun FavoriteItems(
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel,
    primaryValue: String,
    secondValue: String,
    colorCustom: Color,
    onClick: (String?) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    var selectedCode by remember { mutableStateOf<String?>(null) }
    val favorites by viewModel.favorites.collectAsState()

    if (favorites.isEmpty()) {
        EmptyFavoriteScreen(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = primaryValue,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = secondValue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorCustom
                    )
                }
            }

            items(favorites) { country ->

                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            selectedCode = country.code
                            showSheet = true
                            onClick(country.code)
                        },
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
                                model = country.flagUrl,
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
                                    country = country.name,
                                    official = country.official
                                )
                            }

                            IconButton(
                                onClick = { viewModel.removeFavoriteByCode(country.code) },
                                modifier = Modifier.align(Alignment.Top)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    if (showSheet && selectedCode != null) {
        CountryBottomSheet(
            countryDetailsViewModel = viewModel,
            showBottomSheet = true,
            onDismiss = {
                showSheet = false
                selectedCode = null
            }
        )
    }
}