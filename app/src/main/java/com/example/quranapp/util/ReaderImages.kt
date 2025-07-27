package com.example.quranapp.util

import android.content.Context
import com.example.quranapp.R

class ReaderImages{
    companion object{
        fun getReaderImages(context: Context): Map<String, Int> {
            return mapOf(
                context.getString(R.string.sheikh_basfar) to R.drawable.abdullah_basfar,
                context.getString(R.string.sheikh_sudais) to R.drawable.abdurrahmaan_as_sudais,
                context.getString(R.string.sheikh_abdulsamad) to R.drawable.abdulbaset_abdulsamad,
                context.getString(R.string.sheikh_shaatree) to R.drawable.abu_bakr_ash_shaatree,
                context.getString(R.string.sheikh_alajamy) to R.drawable.ahmed_ibn_ali_alajamy,
                context.getString(R.string.sheikh_alafasy) to R.drawable.mashary_rashed,
                context.getString(R.string.sheikh_hani) to R.drawable.hani_ar_rifai,
                context.getString(R.string.sheikh_husary) to R.drawable.husary,
                context.getString(R.string.sheikh_husary_mujawwad) to R.drawable.husary,
                context.getString(R.string.sheikh_hudhaify) to R.drawable.alhudaify,
                context.getString(R.string.sheikh_ibrahim_akhdar) to R.drawable.ibrahim_akhdar,
                context.getString(R.string.sheikh_maher) to R.drawable.maher_elmekly,
                context.getString(R.string.sheikh_ayyoub) to R.drawable.mohamed_ayoub,
                context.getString(R.string.sheikh_jibreel) to R.drawable.mohamed_jebril,
                context.getString(R.string.sheikh_shuraym) to R.drawable.saoud_shuraim,
                context.getString(R.string.sheikh_parhizgar) to R.drawable.parhizgar,
                context.getString(R.string.sheikh_sowaid) to R.drawable.ayman_swid
            )
        }

    }
}