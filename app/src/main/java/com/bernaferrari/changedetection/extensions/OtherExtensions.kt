package com.bernaferrari.changedetection.extensions

import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import java.util.*

fun Long.convertTimestampToDate(): String {
    val messages = TimeAgoMessages.Builder().withLocale(Locale.getDefault()).build()
    return TimeAgo.using(this, messages)
}

fun View.getText(@StringRes res: Int) = this.resources.getText(res)
operator fun Boolean.inc() = !this

fun ImageView.setAndStartAnimation(res: Int, context: Context) {
    this.setImageDrawable(
        AnimatedVectorDrawableCompat.create(
            context,
            res
        )
    )
    (this.drawable as AnimatedVectorDrawableCompat).start()
}

// this will scroll to wanted index + or - one, giving a margin of one and allowing user to
// keep tapping in the same place and RecyclerView keep scrolling.
fun RecyclerView.scrollToIndexWithMargins(
    previousIndex: Int,
    index: Int,
    size: Int
) {
    if (previousIndex == -1) {
        // This will run on first iteration
        when (index) {
            in 1 until (size - 1) -> this.scrollToPosition(index - 1)
            (size - 1) -> this.scrollToPosition(index)
            else -> this.scrollToPosition(0)
        }
    } else {
        when (index) {
            in 1 until previousIndex -> this.scrollToPosition(index - 1)
            in previousIndex until (size - 1) -> this.scrollToPosition(index + 1)
            else -> this.scrollToPosition(index)
        }
    }
}

fun Int.toDp(resources: Resources): Int {
    return (resources.displayMetrics.density * this).toInt()
}