package com.example.quranapp.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.api.viewmodel.ApiViewModel
import com.example.quranapp.language.LanguageViewModel
import com.example.quranapp.navigation.MainNavigationViewModel
import com.example.quranapp.player.AudioPlayerService
import com.example.quranapp.player.PlayerViewModel
import com.example.quranapp.screens.helpercomposable.CollapsedBottomSheet
import com.example.quranapp.screens.helpercomposable.ExpandedBottomSheet
import com.example.quranapp.screens.helpercomposable.TopBar
import com.example.quranapp.screens.model.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheet(
    content: @Composable (PaddingValues) -> Unit
){
    var scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val playerViewModel: PlayerViewModel = hiltViewModel()
    val apiViewModel: ApiViewModel = hiltViewModel()
    val navigationViewModel: MainNavigationViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = viewModel()

    var isExpanded by remember { mutableStateOf(false) }
    val surah by apiViewModel.surahState.collectAsState()
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    val readerName = if (selectedLanguage == "ar") surah?.data?.edition?.name else surah?.data?.edition?.englishName
    val surahName = if (selectedLanguage == "ar") surah?.data?.name else surah?.data?.englishName
    val showBottomSheet = apiViewModel.showBottomSheet

    LaunchedEffect(scaffoldState.bottomSheetState) {
        snapshotFlow { scaffoldState.bottomSheetState.currentValue }
            .collect { sheetValue ->
                isExpanded = when (sheetValue) {
                    SheetValue.Expanded -> true
                    SheetValue.PartiallyExpanded -> false
                    else -> false
                }
            }
        playerViewModel.changeIsExpanded(isExpanded)
    }

    LaunchedEffect(Unit, surah) {
        surah?.data?.ayahs.let {
            playerViewModel.play(it ?: emptyList())
            playerViewModel.playerManager.surahName = surahName
            playerViewModel.playerManager.readerName = readerName
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        sheetPeekHeight = if (showBottomSheet && surah != null) 100.dp else 0.dp,
        sheetDragHandle = null,
        sheetShape = RectangleShape,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        sheetContent = {
            if (!isExpanded){
                CollapsedBottomSheet()
            }else{
                ExpandedBottomSheet {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            }
        },
        topBar = {
            if (
                navigationViewModel.backStack.lastOrNull() !is Screens.DisplayAzkarScreen &&
                navigationViewModel.backStack.lastOrNull() !is Screens.DisplayDuaaScreen
                ){
                navigationViewModel.backStack.lastOrNull()
                    ?.let { TopBar(screenName = stringResource(it.screenName)) }
                    ?: TopBar("")
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}