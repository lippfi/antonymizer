package fi.lipp.plugin.antonymizer.antonyms

import com.intellij.psi.PsiElement
import fi.lipp.plugin.antonymizer.util.Direction

interface AntonymProvider {
    fun getAntonym(element: PsiElement?, word: String, direction: Direction): String? {
        return getLanguageAwareAntonym(element, direction) ?: getPlainTextAntonym(word, direction)
    }
    
    fun getPlainTextAntonym(word: String, direction: Direction): String?
    fun getLanguageAwareAntonym(element: PsiElement?, direction: Direction): String?
}