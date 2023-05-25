package fi.lipp.plugin.antonymizer.antonyms

import com.intellij.psi.PsiElement
import fi.lipp.plugin.antonymizer.util.BiMap
import fi.lipp.plugin.antonymizer.util.CycleMap
import fi.lipp.plugin.antonymizer.util.Direction

class PlainTextAntonymProvider: AntonymProvider {
    override fun getPlainTextAntonym(word: String, direction: Direction): String? {
        return biAntonyms.get(word, direction) ?: cyclicAntonyms.get(word, direction)
    }
    
    override fun getLanguageAwareAntonym(element: PsiElement?, direction: Direction): String? {
        return null
    }
    
    private val biAntonyms = BiMap(
        mutableMapOf(
            "above" to "below",
            "accept" to "reject",
            "add" to "remove",
            "allow" to "deny",
            "ascent" to "descent",
            "attach" to "detach",
            "before" to "after",
            "compress" to "decompress",
            "connect" to "disconnect",
            "enable" to "disable",
            "encode" to "decode",
            "encode" to "decode",
            "encrypt" to "decrypt",
            "entry" to "exit",
            "expand" to "collapse",
            "first" to "last",
            "forward" to "backward",
            "forward" to "reverse",
            "import" to "export",
            "in" to "out",
            "inclusive" to "exclusive",
            "increase" to "decrease",
            "increment" to "decrement",
            "input" to "output",
            "insert" to "delete",
            "internal" to "external",
            "left" to "right",
            "leftmost" to "rightmost",
            "load" to "unload",
            "local" to "global",
            "lock" to "unlock",
            "min" to "max",
            "minimum" to "maximum",
            "next" to "previous",
            "on" to "off",
            "open" to "close",
            "over" to "under",
            "parent" to "child",
            "positive" to "negative",
            "public" to "private",
            "push" to "pop",
            "row" to "col",
            "serialize" to "deserialize",
            "show" to "hide",
            "source" to "destination",
            "start" to "end",
            "success" to "failure",
            "sync" to "async",
            "top" to "bottom",
            "true" to "false",
            "up" to "down",
            "upper" to "lower",
            "vertical" to "horizontal",
            "visible" to "invisible",
            "width" to "height",
            "<" to ">",
            "<=" to ">=",
            "!=" to "==",
            "-" to "+",
        )
    )
    private val cyclicAntonyms = CycleMap(
        arrayOf(
            arrayOf("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"),
            arrayOf("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"),
            arrayOf("winter", "spring", "summer", "autumn"),
            arrayOf("north", "east", "south", "west"),
            arrayOf("red", "orange", "yellow", "green", "blue", "indigo", "violet"),
        )
    )
}