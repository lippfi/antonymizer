package fi.lipp.plugin.antonymizer.actions

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler.ForEachCaret
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import fi.lipp.plugin.antonymizer.model.Subword
import fi.lipp.plugin.antonymizer.model.Word
import fi.lipp.plugin.antonymizer.services.AntonymService
import fi.lipp.plugin.antonymizer.services.WordService
import fi.lipp.plugin.antonymizer.util.Direction

class ReplaceWithNextAntonymAction : EditorAction(ReplaceWithAntonymActionHandler(Direction.FORWARD))
class ReplaceWithPreviousAntonymAction : EditorAction(ReplaceWithAntonymActionHandler(Direction.BACKWARD))

class ReplaceWithAntonymActionHandler(private val direction: Direction) : ForEachCaret() {
    override fun doExecute(editor: Editor, caret: Caret, dataContext: DataContext?) {
        val caretOffset = caret.offset
        val word = WordService.getWordAtOffset(editor, caretOffset) ?: return
        val element = getPsiElementAtOffset(editor, caretOffset)
        val (subword, antonym) = findFirstSubwordWithAntonym(word, element, caretOffset, direction) ?: return
        replaceSubwordWithAntonym(editor, subword, antonym)
        caret.moveToOffset(word.startOffset)
    }

    private fun getPsiElementAtOffset(editor: Editor, offset: Int): PsiElement? {
        val project = editor.project ?: return null

        val documentManager = PsiDocumentManager.getInstance(project)
        val psiFile = documentManager.getPsiFile(editor.document) ?: return null

        return psiFile.findElementAt(offset)
    }

    private fun findFirstSubwordWithAntonym(word: Word, element: PsiElement?, caretOffset: Int, direction: Direction): Pair<Subword, String>? {
        val (subwordsBefore, subwordsAfter) = word.subwords.partition { it.range.endOffset <= caretOffset }
        
        fun Collection<Subword>.firstSubwordWithAntonym(): Pair<Subword, String>? {
            return this.asSequence().mapNotNull { AntonymService.getSubwordAntonymPair(element, it, direction) }.firstOrNull()
        }
        return subwordsAfter.firstSubwordWithAntonym() ?: subwordsBefore.firstSubwordWithAntonym()
    }

    private fun replaceSubwordWithAntonym(editor: Editor, subword: Subword, antonym: String) {
        runWriteAction {
            editor.document.replaceString(subword.startOffset, subword.endOffset, antonym)
        }
    }
}
