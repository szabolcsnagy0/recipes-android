package com.recipes.ui.recipedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.recipes.R
import com.recipes.data.RecipeData
import com.recipes.ui.shared.DifficultyElement
import com.recipes.ui.shared.IconWithText
import com.recipes.ui.shared.StarRating

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeDetails(
    recipe: RecipeData,
    modifier: Modifier = Modifier,
    onStepBack: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val imageOffset = (-40).dp
    LazyColumn(
        contentPadding = PaddingValues(
            bottom = 30.dp
        ),
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
    ) {
        item {
            Box(
                modifier = Modifier
                    .offset(y = imageOffset)
                    .height(300.dp)
                    .clip(RoundedCornerShape(13))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxWidth()
                        .offset(y = -imageOffset)
                        .padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back
                        ),
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onStepBack()
                            }
                    )
                }
                recipe.image?.let {
                    GlideImage(
                        model = it,
                        loading = placeholder(R.drawable.food_placeholder),
                        contentDescription = recipe.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .zIndex(0f)
                    )
                }
            }
        }

        val paddingModifier: Modifier = Modifier.padding(start = 15.dp, end = 10.dp)
        item {
            Column(
                modifier = Modifier
                    .offset(y = imageOffset)
                    .padding(top = 10.dp)
            ) {
                recipe.name?.let {
                    Text(
                        text = it,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 40.sp,
                        modifier = paddingModifier
                    )
                }

                recipe.rating?.let {
                    Row(verticalAlignment = Alignment.Bottom, modifier = paddingModifier) {
                        Text(
                            text = "$it",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 5.dp, top = 5.dp)
                        )
                        StarRating(rating = it)
                    }
                }

                recipe.tags?.let { list ->
                    LazyRow(
                        contentPadding = PaddingValues(
                            end = 10.dp
                        ),
                        modifier = Modifier.padding(vertical = 20.dp)
                    ) {
                        items(list) { name ->
                            name?.let {
                                TagCard(name = it, modifier = Modifier.padding(horizontal = 10.dp))
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    val columnModifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(13))
                        .background(colorResource(id = R.color.light_orange))
                        .padding(vertical = 20.dp)
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = columnModifier
                    ) {
                        recipe.difficulty?.let {
                            DifficultyElement(
                                difficulty = it,
                                color = R.color.black,
                                fontSize = 20.sp,
                                iconSize = 30.dp,
                            )
                        }
                        recipe.prepTimeMinutes?.let { prep ->
                            recipe.cookTimeMinutes?.let { cook ->
                                IconWithText(
                                    text = "${prep + cook} min",
                                    icon = Icons.Filled.Timer,
                                    fontSize = 20.sp,
                                    iconSize = 30.dp
                                )
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = columnModifier
                    ) {
                        recipe.servings?.let { servings ->
                            IconWithText(
                                text = servings.toString(),
                                icon = Icons.Filled.RiceBowl,
                                fontSize = 25.sp,
                                iconSize = 30.dp
                            )

                            recipe.caloriesPerServing?.let { calories ->
                                IconWithText(
                                    text = "${servings * calories} cal",
                                    icon = Icons.Filled.FitnessCenter,
                                    fontSize = 20.sp,
                                    iconSize = 30.dp
                                )
                            }
                        }
                    }
                }
                recipe.instructions?.let { instructions ->
                    Text(
                        text = stringResource(id = R.string.instructions),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = paddingModifier.padding(start = 10.dp, bottom = 10.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(
                            end = 10.dp
                        )
                    ) {
                        itemsIndexed(instructions) { index, item ->
                            item?.let {
                                InstructionCard(
                                    instruction = "${index + 1}. $it",
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(150.dp)
                                        .padding(horizontal = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        recipe.ingredients?.let { ingredients ->
            item {
                Text(
                    text = stringResource(id = R.string.ingredients),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = paddingModifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
                )
            }
            items(ingredients) { item ->
                item?.let {
                    IngredientItem(
                        ingredient = it,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TagCard(name: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.dark_orange),
            contentColor = colorResource(id = R.color.white)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
    ) {
        Text(text = name, fontSize = 17.sp, modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun InstructionCard(instruction: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.light_orange),
            contentColor = colorResource(id = R.color.black)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = instruction,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun IngredientItem(ingredient: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = stringResource(id = R.string.arrow)
        )
        Text(
            text = ingredient,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )
    }
}