package com.enigma.enigmamedia.utils

import com.enigma.enigmamedia.R

class RandomAvatar {
    fun getRandomAvatar(): Int {
        val avatarResourceDrawable = arrayOf(
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4
        )

        val randomIndex = (avatarResourceDrawable.indices).random()
        return avatarResourceDrawable[randomIndex]
    }
}