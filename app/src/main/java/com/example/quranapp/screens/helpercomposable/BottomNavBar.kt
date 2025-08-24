package com.example.quranapp.screens.helpercomposable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.quranapp.R
import com.example.quranapp.screens.model.Screens
import com.example.quranapp.ui.theme.AppPurple

data class BottomNavModel(
    val screens: Screens,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
    )

private val BottomNavItems = listOf(
    BottomNavModel(
        screens = Screens.HomeScreen,
        icon = R.drawable.home,
        label = R.string.home_screen
    ),
    BottomNavModel(
        screens = Screens.QuranScreen,
        icon = R.drawable.quran,
        label = R.string.quran_screen
    ),
    BottomNavModel(
        screens = Screens.PlaylistsScreen,
        icon = R.drawable.playlists,
        label = R.string.playlist_screen
    ),
    BottomNavModel(
        screens = Screens.DivisionsScreen,
        icon = R.drawable.devision,
        label = R.string.division_screen
    )
)

@Composable
fun BottomNavBar(
    currentScreen: Screens?,
    onScreenSelected: (Screens) -> Unit
){
    NavigationBar {
        BottomNavItems.forEach {
            NavigationBarItem(
                selected = currentScreen == it.screens,
                onClick = { onScreenSelected(it.screens) },
                icon = {
                    Icon(
                        painter = painterResource(it.icon),
                        contentDescription = "item",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(it.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = AppPurple,
                    unselectedTextColor = Color.Black,
                    indicatorColor = AppPurple,
                )
            )
        }
    }
}