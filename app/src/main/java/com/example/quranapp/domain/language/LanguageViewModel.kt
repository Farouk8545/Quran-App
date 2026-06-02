package com.example.quranapp.domain.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

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