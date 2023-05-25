package fi.lipp.plugin.antonymizer.parsers

import com.intellij.openapi.util.TextRange
import fi.lipp.plugin.antonymizer.model.Subword

class CamelCaseParser : WordParser {
    override fun extractSubwords(word: String, range: TextRange): List<Subword> {
        val numberSeparatorRanges = Regex("[0-9]+").findAll(word)
            .map { it.range.toExclusive() }
        val caseChangedRanges = Regex("[A-Z]").findAll(word)
            .map { IntRange(it.range.first, it.range.first) }
        val separatorRanges = (numberSeparatorRanges + caseChangedRanges).sortedBy { it.first }.toList()
        return splitWordByRanges(word, range, separatorRanges)
    }
}