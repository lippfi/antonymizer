package fi.lipp.plugin.antonymizer

import com.intellij.openapi.util.TextRange
import fi.lipp.plugin.antonymizer.model.Subword
import fi.lipp.plugin.antonymizer.services.WordService
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SnakeCaseParserTest {
    @Test
    fun `ordinary snake case`() {
        val word = "MY_TRUE_VALUE"
        val subwords = WordService.extractSubwords(word, TextRange(0, word.length))
        assertEquals(3, subwords.size)
        assertEquals(Subword("MY", TextRange(0, 2)), subwords[0])
        assertEquals(Subword("TRUE", TextRange(3, 7)), subwords[1])
        assertEquals(Subword("VALUE", TextRange(8, 13)), subwords[2])
    }
}