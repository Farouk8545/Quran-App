package com.example.quranapp.data.local.assets

class UrlBuilder {

    // Data source base URL
    private val baseUrl = "https://everyayah.com/data/"

    // Builds a list of URLs for the specified surah and reciter
    fun buildUrl(surahNumber: Int, reciterUrlIdentifier: String, firstAyah: Int, lastAyah: Int): List<String>{
        val urls: MutableList<String> = mutableListOf()
        for (it in firstAyah..lastAyah){
            val surahNumberString = surahNumber.toString().padStart(3, '0')
            val ayahNumberString = (it).toString().padStart(3, '0')
            val url = "$baseUrl$reciterUrlIdentifier/$surahNumberString$ayahNumberString.mp3"
            urls.add(url)
        }
        return urls
    }
}