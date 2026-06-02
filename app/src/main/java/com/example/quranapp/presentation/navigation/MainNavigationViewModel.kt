package com.example.quranapp.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.quranapp.presentation.screens.model.Screens

class MainNavigationViewModel: ViewModel() {
    var backStack = mutableStateListOf<Screens>(Screens.HomeScreen)

}