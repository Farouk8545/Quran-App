package com.example.quranapp.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageViewModel: ViewModel() {

    private val _selectedLanguage = MutableStateFlow<String>(getCurrentLanguage())
    val selectedLanguage = _selectedLanguage.asStateFlow()

    fun changeLanguage(language: String){
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
        _selectedLanguage.value = language
    }

    fun getCurrentLanguage(): String{
        return AppCompatDelegate.getApplicationLocales().get(0)?.language ?: "ar"
    }

}