package com.example.quranapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.navigation.MainNavigation
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.player.ApiViewModelBridge
import com.example.quranapp.player.PlayerViewModel
import com.example.quranapp.screens.DisplayAzkarScreen
import com.example.quranapp.screens.PlayerBottomSheet
import com.example.quranapp.screens.SettingsScreen
import com.example.quranapp.screens.helpercomposable.BottomNavBar
import com.example.quranapp.screens.model.Screens
import com.example.quranapp.ui.theme.QuranAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuranAppTheme {

                val navigationViewModel: MainNavigationViewModel = viewModel()
                val apiViewModel: ApiViewModel = hiltViewModel()
                val playerViewModel: PlayerViewModel = hiltViewModel()

                val context = LocalContext.current

                var isExpanded by remember { mutableStateOf(false) }

                LaunchedEffect(apiViewModel.errorMessage) {
                    if(apiViewModel.errorMessage != null){
                        Toast.makeText(context, apiViewModel.errorMessage, Toast.LENGTH_SHORT).show()
                        apiViewModel.errorMessage = null
                    }
                }

                LaunchedEffect(Unit) {
                    ApiViewModelBridge.apiViewModel = apiViewModel

                    playerViewModel.isExpanded.collect { state ->
                        isExpanded = state
                    }
                }

                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (!isExpanded && navigationViewModel.backStack.lastOrNull() !is Screens.DisplayAzkarScreen){
                            BottomNavBar(
                                currentScreen = navigationViewModel.backStack.lastOrNull(),
                            ) {
                                navigationViewModel.backStack.removeLastOrNull()
                                navigationViewModel.backStack.add(it)
                            }
                        }
                    }
                ){ outerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(outerPadding)
                    ){
                        PlayerBottomSheet { bottomSheetPadding ->
                            Box (
                                modifier = Modifier.fillMaxSize()
                                    .padding(bottomSheetPadding)
                            ){
                                MainNavigation()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun Preview(){
    DisplayAzkarScreen(
        emptyList(),
        R.string.azkar
    )
}
