package com.blackjade.pinyintextview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView

class PinyinTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {
    private var pinyins = emptyList<String>()
    private var letters = mutableListOf<String>()

    override fun onDraw(canvas: Canvas) {
        if (text != null && text.matches(Regex("^.*\\d"))) {
            pinyins = text.replace(Regex("v"), "u").split(Regex("(?<=\\d)"))
            letters = text.replace(Regex("v"), "u").split(Regex("\\d")).toMutableList()
        }

        var x = 0f
        for (i in pinyins.indices) {
            var pinyin = pinyins[i]
            if (pinyin.isNotEmpty()) {
                var y = paint.fontMetrics.leading - paint.fontMetrics.top

                val toneNumber = pinyin[pinyin.length - 1]
                val tone = getTone(toneNumber)
                val stateIndex = getStateIndex(pinyin)
                if (stateIndex != -1) {

                    // If tone is above 'ü', move tone up a bit
                    if (pinyin[stateIndex] == 'ü') {
                        y -= resources.displayMetrics.density * 4
                    }

                    // If tone is above 'i', replace 'i' by 'ı'
                    if (pinyin[stateIndex] == 'i') {
                        letters[i] = letters[i].replace('i', 'ı')
                    }
                    canvas.drawText(
                        tone,
                        x + paint.measureText(pinyin.substring(0, stateIndex))
                                + (paint.measureText(pinyin[stateIndex].toString()) - paint.measureText(tone)) / 2,
                        y,
                        paint
                    )
                }
                val letter = pinyin.substring(0, pinyin.length - 1) + " "
                x += paint.measureText(letter)
            }
        }

        if (text != null && text.matches(Regex("^.*\\d"))) {
            text = letters.joinToString(" ")
        }
        super.onDraw(canvas)
    }

    private fun getTone(toneNumber : Char) : String =
        when (toneNumber) {
            '1' -> "ˉ"
            '2' -> "ˊ"
            '3' -> "ˇ"
            '4' -> "ˋ"
            else -> " "
        }

    private fun getStateIndex(text: String) : Int {
        var toneIndex = text.length - 1
        var stateIndex = -1
        while (toneIndex >= 0) {
            if (text[toneIndex] == 'a'
                || text[toneIndex] == 'e'
                || text[toneIndex] == 'i'
                || text[toneIndex] == 'o'
                || text[toneIndex] == 'u'
                || text[toneIndex] == 'ü') {
                if (stateIndex == -1 || text[toneIndex] < text[stateIndex]) {
                    stateIndex = toneIndex
                }
            }
            toneIndex--
        }

        // if 'iu' in pinyin
        if (text.contains("u")
            && text.contains("i")
            && !text.contains("a")
            && !text.contains("o")
            && !text.contains("e")
        ) {
            if (text.indexOf("u") > text.indexOf("i")) {
                stateIndex = text.indexOf("u")
            } else {
                stateIndex = text.indexOf("i")
            }
        }
        return stateIndex
    }
}