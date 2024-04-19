package com.recipes.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recipes.R
import com.recipes.data.Difficulty

@Composable
fun IconWithText(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    color: Int = R.color.black,
    fontSize: TextUnit = 15.sp,
    iconSize: Dp = 20.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = colorResource(id = color),
            modifier = Modifier
                .padding(end = 5.dp)
                .size(iconSize)
        )
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(
                id = color
            )
        )
    }
}

@Composable
fun DifficultyElement(
    difficulty: Difficulty,
    modifier: Modifier = Modifier,
    color: Int = R.color.black,
    fontSize: TextUnit = 15.sp,
    iconSize: Dp = 20.dp
) {
    val (icon, text) = when (difficulty) {
        Difficulty.EASY -> {
            Icons.Filled.SentimentVerySatisfied to stringResource(id = R.string.easy)
        }

        Difficulty.MEDIUM -> {
            Icons.Filled.SentimentSatisfiedAlt to stringResource(id = R.string.medium)
        }

        Difficulty.HARD -> {
            Icons.Filled.SentimentDissatisfied to stringResource(id = R.string.hard)
        }
    }
    IconWithText(
        text = text,
        icon = icon,
        color = color,
        modifier = modifier,
        fontSize = fontSize,
        iconSize = iconSize
    )
}