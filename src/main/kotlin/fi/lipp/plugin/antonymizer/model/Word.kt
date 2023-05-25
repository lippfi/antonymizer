package fi.lipp.plugin.antonymizer.model

import com.intellij.openapi.util.TextRange

data class Word(val subwords: List<Subword>, val range: TextRange) {
    constructor(value: String, range: TextRange) : this(listOf(Subword(value, range)), range)

    val startOffset = range.startOffset
}
