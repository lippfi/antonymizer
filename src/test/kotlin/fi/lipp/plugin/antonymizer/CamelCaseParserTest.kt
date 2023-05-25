package fi.lipp.plugin.antonymizer

import com.intellij.openapi.util.TextRange
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import fi.lipp.plugin.antonymizer.model.Subword
import fi.lipp.plugin.antonymizer.services.WordService
import org.junit.Test

class CamelCaseParserTest {
    @Test
    fun `ordinary camel case`() {
        val word = "myTrueValue"
        val subwords = WordService.extractSubwords(word, TextRange(0, word.length))
        BasePlatformTestCase.assertEquals(3, subwords.size)
        BasePlatformTestCase.assertEquals(Subword("my", TextRange(0, 2)), subwords[0])
        BasePlatformTestCase.assertEquals(Subword("True", TextRange(2, 6)), subwords[1])
        BasePlatformTestCase.assertEquals(Subword("Value", TextRange(6, 11)), subwords[2])
    }
}