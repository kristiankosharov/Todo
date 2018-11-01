package demo.todosample.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import demo.todosample.R


class StrikeThroughTextView : TextView {
    lateinit var paint: Paint
    var addStrike = false

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private fun init(context: Context) {
        paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.iconColor)
        paint.strokeWidth = resources.displayMetrics.density * 1
    }

    fun strikeThrough() {
        addStrike = true
        setTextColor(ContextCompat.getColor(context, R.color.lightGray))
        invalidate()
    }

    fun release() {
        addStrike = false
        setTextColor(ContextCompat.getColor(context, R.color.dark))
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (addStrike) {
            val bounds = Rect()
            getPaint().getTextBounds(text.toString(), 0, text.toString().length, bounds)
            canvas.drawLine(-10f, (measuredHeight / 2).toFloat(), bounds.width().toFloat() + 10F,
                    (measuredHeight / 2).toFloat(), paint)
        }
    }
}