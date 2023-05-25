package fi.lipp.plugin.antonymizer

import fi.lipp.plugin.antonymizer.services.WordService
import com.intellij.openapi.components.service
import com.intellij.openapi.util.TextRange
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import fi.lipp.plugin.antonymizer.model.Subword

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase() {
    fun `test parse snake case 1`() {
        val wordService = service<WordService>()
        val word = "MY_TRUE_VALUE"
        val subwords = wordService.extractSubwords(word, TextRange(0, word.length))
        assertEquals(3, subwords.size)
        assertEquals(Subword("MY", TextRange(0, 2)), subwords[0])
        assertEquals(Subword("TRUE", TextRange(3, 7)), subwords[1])
        assertEquals(Subword("VALUE", TextRange(8, 13)), subwords[2])
    }
}
