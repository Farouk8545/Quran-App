package com.example.quranapp.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.quranapp.screens.model.Screens

class MainNavigationViewModel: ViewModel() {
    var backStack = mutableStateListOf<Screens>(Screens.HomeScreen)

}