package com.recipes.ui.recipeslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.recipes.R
import com.recipes.data.RecipeData
import com.recipes.ui.shared.DifficultyElement
import com.recipes.ui.shared.StarRating

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListItem(
    recipe: RecipeData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(vertical = 20.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(15),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 7.dp),
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            recipe.name?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(
                        id = R.color.black
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    recipe.image?.let {
                        GlideImage(
                            model = it,
                            loading = placeholder(R.drawable.food_placeholder),
                            contentDescription = recipe.name,
                            modifier = Modifier
                                .size(110.dp)
                                .clip(RoundedCornerShape(13)),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.ingredients),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(
                            id = R.color.black
                        )
                    )
                    val shownIngredients = 4
                    recipe.ingredients?.take(shownIngredients)?.forEach {
                        it?.let {
                            Text(
                                text = it,
                                fontSize = 12.sp,
                                lineHeight = 20.sp,
                                maxLines = 1
                            )
                        }
                    }
                    if ((recipe.ingredients?.size ?: 0) > shownIngredients) {
                        Text(text = "...", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, lineHeight = 10.sp)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarRating(rating = recipe.rating, modifier = Modifier.weight(1f))
                recipe.difficulty?.let {
                    DifficultyElement(it, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}