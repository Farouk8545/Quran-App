package com.example.quranapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.screens.helpercomposable.AzkarCard
import com.example.quranapp.screens.model.Screens
import com.example.quranapp.util.DuaaIcons

@Composable
fun DuaaScreen(){

    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()

    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items (DuaaIcons.duaaCategoryMap.entries.toList()){ (title, icon) ->
            AzkarCard(
                icon = icon,
                title = title
            ) {
                navViewModel.backStack.add(Screens.DisplayDuaaScreen(apiViewModel.getDuaa(title) ?: emptyList(), title))
            }
        }
    }
}