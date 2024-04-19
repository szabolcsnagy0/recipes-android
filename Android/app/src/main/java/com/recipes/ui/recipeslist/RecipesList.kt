package com.recipes.ui.recipeslist

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.recipes.R
import com.recipes.data.RecipeData

@Composable
fun RecipesList(
    viewModel: ListViewModel,
    onItemSelected: () -> Unit
) {
    val recipes by viewModel.recipes.observeAsState()

    val searchQuery by viewModel.searchQuery.observeAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery ?: "", onValueChange = {
                viewModel.searchQuery.value = it
            },
            maxLines = 1,
            shape = RoundedCornerShape(13.dp),
            label = {
                Text(text = stringResource(R.string.search))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search, contentDescription = stringResource(
                        id = R.string.search
                    )
                )
            },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = 30.dp
            )
        ) {
            items(recipes ?: emptyList()) { item ->
                item?.let {
                    ListItem(it) {
                        viewModel.selectItem(it)
                        onItemSelected()
                    }
                }
            }
        }
    }
}