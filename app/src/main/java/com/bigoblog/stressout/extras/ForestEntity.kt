package com.bigoblog.stressout.extras

import android.media.MediaPlayer

data class ForestEntity(val title : String,
                        val photo : Int,
                        val forestSound : MediaPlayer? = null,
                        var isSelected : Boolean = false)
