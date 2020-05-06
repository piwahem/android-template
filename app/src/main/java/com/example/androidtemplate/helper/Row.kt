package com.example.androidtemplate.helper

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidtemplate.R
import timber.log.Timber


class Row : ConstraintLayout {

    var tvTitle: TextView? = null
    var tvValue: TextView? = null

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    )
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    )
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        var title: CharSequence = ""
        var value: CharSequence = ""
        try {
            attrs?.let {
                val typedArray = context.obtainStyledAttributes(it, R.styleable.Row, 0, 0)
                val hasRowTitle = typedArray.hasValue(R.styleable.Row_rowTitle)
                val hasRowValue = typedArray.hasValue(R.styleable.Row_rowValue)

                title = if (hasRowTitle) {
                    typedArray.getText(R.styleable.Row_rowTitle)
                } else ""
                value = if (hasRowValue) {
                    typedArray.getText(R.styleable.Row_rowValue)
                } else ""

                typedArray.recycle()
            }

            LayoutInflater.from(context).inflate(R.layout.view_custom_row, this, true)
            tvTitle = findViewById<TextView>(R.id.tvTitle)
            tvValue = findViewById<TextView>(R.id.tvValue)

            tvTitle?.text = title
            tvValue?.text = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}