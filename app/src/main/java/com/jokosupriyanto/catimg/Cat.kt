package com.jokosupriyanto.catimg

data class Cat(
    val id: Long = counter++,
    val name: String,
    val url: String
) {
    companion object {
        private var counter = 0L
    }

    fun getCat(): Cat {
        return this
    }
}
