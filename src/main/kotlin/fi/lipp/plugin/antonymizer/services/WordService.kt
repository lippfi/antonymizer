package fi.lipp.plugin.antonymizer.services

import fi.lipp.plugin.antonymizer.model.Subword
import fi.lipp.plugin.antonymizer.model.Word
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import fi.lipp.plugin.antonymizer.parsers.CamelCaseParser
import fi.lipp.plugin.antonymizer.parsers.SnakeCaseParser
import fi.lipp.plugin.antonymizer.parsers.WordParser

object WordService {
    private val snakeCaseParser = SnakeCaseParser()
    private val camelCaseParser = CamelCaseParser()
    
    fun getWordAtOffset(editor: Editor, offset: Int): Word? {
        return getJavaIdentifierWord(editor, offset) ?: getSpecialCharWord(editor, offset)
    }
    
    private fun getJavaIdentifierWord(editor: Editor, offset: Int): Word? {
        val wordRange = getTextRangeByCondition(editor, offset) { it.isJavaIdentifierPart() } ?: return null
        val wordText = editor.document.getText(wordRange)
        val subwords = extractSubwords(wordText, wordRange)
        return Word(subwords, wordRange)
    }

    private fun getSpecialCharWord(editor: Editor, offset: Int): Word? {
        val wordRange = getTextRangeByCondition(editor, offset) { it.isSpecialChar() } ?: return null
        val wordText = editor.document.getText(wordRange)
        return Word(wordText, wordRange)
    }

    private fun getTextRangeByCondition(editor: Editor, offset: Int, condition: (Char) -> Boolean): TextRange? {
        val charSequence = editor.document.charsSequence

        val preprocessedOffset = calculatePreprocessedOffset(charSequence, offset)
        if (!condition(charSequence[preprocessedOffset])) return null

        val startOffset = findStartOffset(charSequence, preprocessedOffset, condition)
        val endOffset = findEndOffset(charSequence, preprocessedOffset, condition)

        return TextRange(startOffset, endOffset + 1)
    }

    /**
     * The purpose of this preprocessing is to adjust the position of the caret
     * in case it is located right after a word and followed by a space character.
     * We assume that the user expects to get the word to the left of the caret,
     * for this reason, we might subtract 1 from the given offset to place the caret inside the word.
     */
    private fun calculatePreprocessedOffset(charSequence: CharSequence, offset: Int): Int {
        return if (offset == charSequence.length || charSequence[offset].isWhitespace()) {
            (offset - 1).coerceAtLeast(0)
        } else {
            offset
        }
    }

    private fun findStartOffset(charSequence: CharSequence, offset: Int, condition: (Char) -> Boolean): Int {
        return generateSequence(offset) { it - 1 }
            .takeWhile { it >= 0 && condition(charSequence[it]) }
            .last()
    }

    private fun findEndOffset(charSequence: CharSequence, offset: Int, condition: (Char) -> Boolean): Int {
        return generateSequence(offset) { it + 1 }
            .takeWhile { it < charSequence.length && condition(charSequence[it]) }
            .last()
    }

    fun extractSubwords(word: String, range: TextRange): List<Subword> {
        assert(word.length == range.length)
        val parser = getSuitableWordParser(word) ?: return emptyList()
        return parser.extractSubwords(word, range)
    }
    
    private fun getSuitableWordParser(word: String) : WordParser? {
        return if (word.matches(Regex("[A-Z0-9_]+"))) {
            snakeCaseParser
        } else if (word.matches(Regex("[a-zA-Z0-9]+"))) {
            camelCaseParser
        } else {
            null
        }
    }

    private fun Char.isSpecialChar(): Boolean {
        return !this.isLetterOrDigit() && !this.isWhitespace()
    }
}