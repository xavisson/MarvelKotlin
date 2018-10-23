package com.xavisson.marvel.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

enum class OnTextChangedMode {
    ALWAYS, WHEN_IN_FOCUS
}

fun EditText.setupEditTextOnTextChangedListener(
        mode: OnTextChangedMode = OnTextChangedMode.ALWAYS, onTextChangedBlock: (String) -> Unit
) {
    addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (mode == OnTextChangedMode.WHEN_IN_FOCUS && isFocused) {
                onTextChangedBlock(s.toString())
            } else if (mode == OnTextChangedMode.ALWAYS) {
                onTextChangedBlock(s.toString())
            }
        }
    })
}