package com.bigoblog.stressout.extras

import android.media.MediaPlayer

interface OnClickListener {
    fun setOnClickListener(cat : CatEntity){}


    fun setOnClickListener(forest : ForestEntity){}
    fun setupSounds() : MutableList<MediaPlayer>
    fun setdownSounds()


}