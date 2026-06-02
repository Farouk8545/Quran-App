package com.example.quranapp.presentation.screens.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.data.util.AzkarIcons.Companion.azkarMap
import com.example.quranapp.presentation.navigation.MainNavigationViewModel
import com.example.quranapp.presentation.screens.helpercomposable.AzkarCard
import com.example.quranapp.presentation.screens.model.Screens
import com.example.quranapp.presentation.viewmodels.ApiViewModel

@Composable
fun AzkarScreen(){

    val navViewModel: MainNavigationViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(azkarMap.entries.toList()){ (title, icon) ->
            AzkarCard(
                icon = icon,
                title = title
            ) {
                navViewModel.backStack.add(Screens.DisplayAzkarScreen(apiViewModel.getAzkar(title) ?: emptyList(), title))
            }
        }
    }
}