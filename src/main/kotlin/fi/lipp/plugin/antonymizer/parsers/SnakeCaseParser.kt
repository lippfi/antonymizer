package fi.lipp.plugin.antonymizer.parsers

import com.intellij.openapi.util.TextRange
import fi.lipp.plugin.antonymizer.model.Subword

class SnakeCaseParser : WordParser {
    override fun extractSubwords(word: String, range: TextRange): List<Subword> {
        val separator = Regex("[0-9_]+")
        val separatorRanges = separator.findAll(word).map { it.range.toExclusive() }.toList()
        return splitWordByRanges(word, range, separatorRanges)
    }
}