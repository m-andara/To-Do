package com.example.todo.models

import androidx.annotation.DrawableRes
import com.example.todo.R


enum class Priority(
    val priorityindex: Int,
    @DrawableRes val drawable: Int
) {
    HIGH_PRIORITY(0, R.drawable.ic_high_priority),
    MEDIUM_PRIORITY(1, R.drawable.ic_medium_priority),
    LOW_PRIORITY(2, R.drawable.ic_low_priority);

    companion object {
        fun of(priorityindex: Int) = when(priorityindex) {
            0 -> HIGH_PRIORITY
            1 -> MEDIUM_PRIORITY
            else -> LOW_PRIORITY
        }
    }
}

data class ToDo(
    val name: String,
    val priority: Priority
)