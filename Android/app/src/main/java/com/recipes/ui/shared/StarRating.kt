package com.recipes.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.recipes.R
import kotlin.math.floor

@Composable
fun StarRating(rating: Double?, modifier: Modifier = Modifier) {
    rating?.let {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Bottom
        ){
            val maxStarCount = 5
            val starCount = floor(it).toInt()
            for (i in 1..starCount) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = stringResource(id = R.string.rating),
                    tint = colorResource(id = R.color.dark_orange)
                )
            }
            for (i in starCount ..< maxStarCount) {
                Icon(
                    Icons.Filled.StarOutline,
                    contentDescription = stringResource(id = R.string.rating),
                    tint = colorResource(id = R.color.dark_orange)
                )
            }
        }
    }
}