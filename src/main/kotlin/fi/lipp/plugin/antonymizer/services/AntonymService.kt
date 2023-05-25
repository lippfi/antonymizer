package fi.lipp.plugin.antonymizer.services

import com.intellij.psi.PsiElement
import fi.lipp.plugin.antonymizer.antonyms.PlainTextAntonymProvider
import fi.lipp.plugin.antonymizer.model.Subword
import fi.lipp.plugin.antonymizer.util.Direction
import java.util.*

object AntonymService {
    private val plainTextAntonymProvider = PlainTextAntonymProvider()
    
    fun getSubwordAntonymPair(element: PsiElement?, subword: Subword, direction: Direction): Pair<Subword, String>? {
        val antonym = plainTextAntonymProvider.getAntonym(element, subword.value.lowercase(), direction) ?: return null
        val style = getWordStyle(subword.value)
        return Pair(subword, formatWord(antonym, style))
    }
    
    private fun getWordStyle(word: String): WordStyle {
        return if (word.matches(Regex("[A-Z]+"))) {
            WordStyle.UPPER
        } else if (word.matches(Regex("[A-Z][a-z]*"))) {
            WordStyle.CAPITALIZED
        } else if (word.matches(Regex("[a-z]+"))) {
            WordStyle.LOWER
        } else {
            WordStyle.AS_IS
        }
    }
    
    private fun formatWord(word: String, style: WordStyle): String {
        return when (style) {
            WordStyle.UPPER -> word.uppercase(Locale.getDefault())
            WordStyle.LOWER -> word.lowercase(Locale.getDefault())
            WordStyle.CAPITALIZED -> word[0].uppercase() + word.substring(1).lowercase(Locale.getDefault())
            WordStyle.AS_IS -> word
        }
    }
    
    private enum class WordStyle {
        UPPER,
        CAPITALIZED,
        LOWER,
        AS_IS,
    }
}
