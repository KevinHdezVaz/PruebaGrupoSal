package com.hevaz.pruebagruposal.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import androidx.recyclerview.widget.RecyclerView

class StickeyHeaderItemDecoration(private val context: Context, private val getHeaderName: (Int) -> String) : RecyclerView.ItemDecoration() {
    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 40f
        color = Color.BLACK
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    private val paint = Paint().apply {
        color = Color.LTGRAY
    }
    private val textHeight = textPaint.descent() - textPaint.ascent()
    private val textOffset = (textHeight / 2) - textPaint.descent()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val totalItemCount = state.itemCount
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        var prevHeader = ""
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)

            if (position != RecyclerView.NO_POSITION && (position == 0 || getHeaderName(position) != getHeaderName(position - 1))) {
                val header = getHeaderName(position)
                if (prevHeader != header) {
                    prevHeader = header
                    val top = child.top.coerceAtLeast(0) + textHeight + textOffset
                    c.drawRect(left.toFloat(), (top - textHeight).toFloat(), right.toFloat(), top.toFloat(), paint)
                    c.drawText(header, left.toFloat(), top - textOffset, textPaint)
                }
            }
        }
    }
}
