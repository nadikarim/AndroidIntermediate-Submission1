package com.nadikarim.submision1.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.nadikarim.submision1.R
import com.uk.tastytoasty.TastyToasty

class CustomPassword: AppCompatEditText
    //View.OnTouchListener
{


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Menambahkan hint pada editText
        hint = "Password"

        // Menambahkan text aligmnet pada editText
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 6) {
                    error = context.getString(R.string.password_error)

                }
            }

            override fun afterTextChanged(s: Editable) {
                //
            }
        })
    }


}