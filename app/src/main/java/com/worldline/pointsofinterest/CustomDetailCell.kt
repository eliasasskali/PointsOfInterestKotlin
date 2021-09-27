package com.worldline.pointsofinterest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout

class CustomDetailCell @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.poi_detail_cell_item, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomDetailCell, defStyle, defStyleRes)
            val drawableResId = typedArray
                .getResourceId(R.styleable.CustomDetailCell_cell_icon, 0)

            val cellIcon = findViewById<ImageView>(R.id.cell_icon)
            cellIcon.setImageResource(drawableResId)

            typedArray.recycle()
        }
    }
}
