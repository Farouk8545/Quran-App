package com.example.quranapp.hilt

import android.app.Application
import com.example.quranapp.fontsize.FontSizeController
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class Application(): Application(){
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            FontSizeController.getFont(applicationContext)
        }
    }
}