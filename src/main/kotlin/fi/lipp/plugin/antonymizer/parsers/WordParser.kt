package fi.lipp.plugin.antonymizer.parsers

import com.intellij.openapi.util.TextRange
import fi.lipp.plugin.antonymizer.model.Subword

sealed interface WordParser {
    fun extractSubwords(word: String, range: TextRange): List<Subword>

    fun splitWordByRanges(word: String, range: TextRange, ranges: List<IntRange>): List<Subword> {
        val result = mutableListOf<Subword>()
        if (ranges.isEmpty()) return listOf(Subword(word, range))

        if (ranges.first().first > 0) {
            result.add(Subword(word, 0, ranges.first().first, range))
        }

        for (i in ranges.indices) {
            if (i >= ranges.size - 1) break

            val currentRange = ranges[i]
            val nexRange = ranges[i + 1]
            result.add(Subword(word, currentRange.last, nexRange.first, range))
        }

        if (ranges.last().last < word.length) {
            result.add(Subword(word, ranges.last().last, word.length, range))
        }

        return result
    }

    fun IntRange.toExclusive(): IntRange {
        return IntRange(first, last + 1)
    }
}