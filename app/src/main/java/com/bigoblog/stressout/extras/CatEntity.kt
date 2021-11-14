package com.bigoblog.stressout.extras

import android.media.MediaPlayer

data class CatEntity(val catName : String,
                     val catDescription : String,
                     val catPhoto : Int,
                     val catSound : MediaPlayer? = null,
                     var isSelected : Boolean = false)
