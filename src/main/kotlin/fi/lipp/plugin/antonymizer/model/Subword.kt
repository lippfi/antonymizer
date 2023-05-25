package fi.lipp.plugin.antonymizer.model

import com.intellij.openapi.util.TextRange

data class Subword(val value: String, val range: TextRange) {
    constructor(word: String, start: Int, end: Int, range: TextRange) : this(
        word.substring(start, end),
        TextRange(range.startOffset + start, range.startOffset + end)
    )
    
    val startOffset = range.startOffset
    val endOffset = range.endOffset
}